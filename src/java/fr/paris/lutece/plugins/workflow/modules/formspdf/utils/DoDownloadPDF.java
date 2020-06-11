/*
 * Copyright (c) 2002-2020, City of Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */
package fr.paris.lutece.plugins.workflow.modules.formspdf.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.CannotLoadBeanClassException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

import fr.paris.lutece.plugins.forms.modules.documentproducer.business.producerconfig.DefaultConfigProducer;
import fr.paris.lutece.plugins.forms.modules.documentproducer.business.producerconfig.DocumentType;
import fr.paris.lutece.plugins.forms.modules.documentproducer.business.producerconfig.IConfigProducer;
import fr.paris.lutece.plugins.forms.modules.documentproducer.service.ConfigProducerService;
import fr.paris.lutece.plugins.forms.modules.documentproducer.service.FormsDocumentProducerPlugin;
import fr.paris.lutece.plugins.forms.modules.documentproducer.utils.PDFUtils;
import fr.paris.lutece.plugins.workflow.modules.formspdf.business.TaskCreatePDFConfig;
import fr.paris.lutece.plugins.workflow.modules.formspdf.service.RequestAuthenticatorService;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.portal.service.util.AppLogService;

/**
 *
 * DoDownloadPDF
 *
 */
public class DoDownloadPDF
{
    /**
     * Do download pdf to Front User
     * 
     * @param request
     *            request
     * @param response
     *            response
     */
    public void doDownloadFile( HttpServletRequest request, HttpServletResponse response )
    {
        ITaskConfigService taskCreatePDFConfigService = SpringContextService.getBean( FormsPDFConstants.BEAN_CREATE_PDF_CONFIG_SERVICE );
        TaskCreatePDFConfig taskCreatePDFConfig = taskCreatePDFConfigService
                .findByPrimaryKey( Integer.parseInt( request.getParameter( FormsPDFConstants.PARAMETER_ID_TASK ) ) );
        String strIdConfig = Integer.toString( taskCreatePDFConfig.getIdConfig( ) );
        int nIdFormResponse = Integer.parseInt( request.getParameter( FormsPDFConstants.PARAMETER_ID_FORM_RESPONSE ) );

        if ( !RequestAuthenticatorService.getRequestAuthenticatorForUrl( ).isRequestAuthenticated( request ) || StringUtils.isBlank( strIdConfig ) )
        {
            return;
        }
        
        ConfigProducerService manageConfigProducerService = null;

        try
        {
            manageConfigProducerService = SpringContextService.getBean( "forms-documentproducer.manageConfigProducer" );
        }
        catch( CannotLoadBeanClassException | NoSuchBeanDefinitionException | BeanDefinitionStoreException e )
        {
            AppLogService.error( e.getMessage( ), e );
        }

        if ( manageConfigProducerService != null )
        {
            Plugin plugin = PluginService.getPlugin( FormsDocumentProducerPlugin.PLUGIN_NAME );
            int nIdConfig = Integer.parseInt( strIdConfig );
            IConfigProducer config;

            if ( ( nIdConfig == -1 ) || ( nIdConfig == 0 ) )
            {
                config = manageConfigProducerService.loadDefaultConfig( plugin, nIdConfig, DocumentType.PDF );
            }
            else
            {
                config = manageConfigProducerService.loadConfig( plugin, nIdConfig );
            }

            if ( config == null )
            {
                config = new DefaultConfigProducer( );
            }

            PDFUtils.doDownloadPDF( request, response, plugin, config,
                    manageConfigProducerService.loadListConfigQuestion( plugin, Integer.parseInt( strIdConfig ) ), request.getLocale( ), nIdFormResponse );
        }
    }
}
