/*
 * Copyright (c) 2002-2015, Mairie de Paris
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
package fr.paris.lutece.plugins.workflow.modules.formspdf.service;


import fr.paris.lutece.plugins.forms.business.FormQuestionResponse;
import fr.paris.lutece.plugins.forms.business.FormQuestionResponseHome;
import fr.paris.lutece.plugins.forms.business.FormResponse;
import fr.paris.lutece.plugins.forms.business.FormResponseHome;
import fr.paris.lutece.plugins.forms.business.Question;
import fr.paris.lutece.plugins.forms.business.QuestionHome;
import fr.paris.lutece.plugins.genericattributes.business.Entry;
import fr.paris.lutece.plugins.genericattributes.business.EntryHome;
import fr.paris.lutece.plugins.genericattributes.business.Field;
import fr.paris.lutece.plugins.genericattributes.business.Response;
import fr.paris.lutece.plugins.genericattributes.business.ResponseHome;
import fr.paris.lutece.plugins.workflow.modules.formspdf.business.TaskCreatePDFConfig;
import fr.paris.lutece.plugins.workflow.modules.formspdf.utils.FormsPDFConstants;
import fr.paris.lutece.plugins.workflowcore.business.resource.ResourceHistory;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.resource.IResourceHistoryService;
import fr.paris.lutece.plugins.workflowcore.service.task.SimpleTask;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.util.AppPathService;
import fr.paris.lutece.portal.service.util.AppPropertiesService;
import fr.paris.lutece.util.url.UrlItem;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Named;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * TaskCreatePDF
 *
 */
public class TaskCreatePDF extends SimpleTask
{

    // PARAMETERS
    private static final String PARAM_SIGNATURE = "signature";
    private static final String PARAM_TIMESTAMP = "timestamp";
    
    //MESSAGES
    private static final String MESSAGE_TASK_TITLE = "module.workflow.formspdf.task_create_pdf_title";

    // SERVICES
    @Inject
    @Named( FormsPDFConstants.BEAN_CREATE_PDF_CONFIG_SERVICE )
    private ITaskConfigService _taskCreatePDFConfigService;
    @Inject
    private IResourceHistoryService _resourceHistoryService;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTitle( Locale locale )
    {
        return I18nService.getLocalizedString( MESSAGE_TASK_TITLE, locale );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void processTask( int nIdResourceHistory, HttpServletRequest request, Locale locale )
    {
        ResourceHistory resourceHistory = _resourceHistoryService.findByPrimaryKey( nIdResourceHistory );
        TaskCreatePDFConfig taskCreatePDFConfig = _taskCreatePDFConfigService.findByPrimaryKey( getId(  ) );
        int nIdQuestionPDF = taskCreatePDFConfig.getIdQuestionUrlPDF(  );
        
        Question question  = QuestionHome.findByPrimaryKey( nIdQuestionPDF );
        Entry entry = EntryHome.findByPrimaryKey( question.getIdEntry( ) );
       
        

        
        FormResponse formResponse = FormResponseHome.findByPrimaryKey( resourceHistory.getIdResource(  ) );
        

        if ( formResponse != null )
        {
            List<String> listElements = new ArrayList<>(  );
            listElements.add( Integer.toString( formResponse.getId( ) ) );

            String strTime = Long.toString( new Date(  ).getTime(  ) );

            String strSignature = RequestAuthenticatorService.getRequestAuthenticatorForUrl(  )
                                                             .buildSignature( listElements, strTime );

            StringBuilder sbUrl = new StringBuilder( getBaseUrl( request ) );

            if ( !sbUrl.toString(  ).endsWith( FormsPDFConstants.SLASH ) )
            {
                sbUrl.append( FormsPDFConstants.SLASH );
            }

            UrlItem url = new UrlItem( sbUrl.toString(  ) + FormsPDFConstants.URL_DOWNLOAD_PDF );
            url.addParameter( PARAM_SIGNATURE, strSignature );
            url.addParameter( PARAM_TIMESTAMP, strTime );
            url.addParameter( FormsPDFConstants.PARAMETER_ID_FORM_RESPONSE, formResponse.getId(  ) );
            url.addParameter( FormsPDFConstants.PARAMETER_ID_TASK, taskCreatePDFConfig.getIdTask(  ) );

            List<FormQuestionResponse> listFormQuestionResponse = FormQuestionResponseHome.findFormQuestionResponseByResponseQuestion( formResponse.getId( ), nIdQuestionPDF );
            
            //Remove those form question response
            if( !CollectionUtils.isEmpty( listFormQuestionResponse ))
            {
            listFormQuestionResponse.forEach(
                                    formQuestion -> FormQuestionResponseHome.remove( formQuestion)
                            );
            }
            
            
            // Set the url as form Response value, and save it
            Response response = new Response();
            
            response.setEntry( entry );
            response.setField( new Field( ) );
            response.setFile( null );
            response.setIsImage( false );
            response.setIterationNumber( 0 );
            response.setResponseValue( url.getUrl( ) );
            response.setStatus( 0 );
            response.setToStringValueResponse( url.toString( ) );
            
            List<Response> listResponse=new ArrayList<>( );
            listResponse.add( response );
           
            FormQuestionResponse formQuestionResponse=new FormQuestionResponse( );
            formQuestionResponse.setEntryResponse( listResponse );
            formQuestionResponse.setIdFormResponse( formResponse.getId( ) );
            formQuestionResponse.setQuestion( question );
            
            
            FormQuestionResponseHome.create( formQuestionResponse );
            
        }
    }

    /**
     * Get the base url
     * @param request the HTTP request
     * @return the base url
     */
    private String getBaseUrl( HttpServletRequest request )
    {
        String strBaseUrl = StringUtils.EMPTY;

        if ( request != null )
        {
            strBaseUrl = AppPathService.getBaseUrl( request );
        }
        else
        {
            strBaseUrl = AppPropertiesService.getProperty( FormsPDFConstants.PROPERTY_LUTECE_BASE_URL );

            if ( StringUtils.isBlank( strBaseUrl ) )
            {
                strBaseUrl = AppPropertiesService.getProperty( FormsPDFConstants.PROPERTY_LUTECE_PROD_URL );
            }
        }

        return strBaseUrl;
    }
}
