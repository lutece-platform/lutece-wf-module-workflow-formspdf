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
package fr.paris.lutece.plugins.workflow.modules.formspdf.web;


import fr.paris.lutece.plugins.forms.business.FormHome;
import fr.paris.lutece.plugins.forms.business.Question;
import fr.paris.lutece.plugins.forms.business.QuestionHome;
import fr.paris.lutece.plugins.forms.modules.documentproducer.business.producerconfig.ConfigProducer;
import fr.paris.lutece.plugins.forms.modules.documentproducer.business.producerconfig.ConfigProducerHome;
import fr.paris.lutece.plugins.forms.modules.documentproducer.business.producerconfig.DocumentType;
import fr.paris.lutece.plugins.forms.modules.documentproducer.service.FormsDocumentProducerPlugin;
import fr.paris.lutece.plugins.genericattributes.business.Entry;
import fr.paris.lutece.plugins.workflow.modules.formspdf.business.TaskCreatePDFConfig;
import fr.paris.lutece.plugins.workflow.modules.formspdf.utils.FormsPDFConstants;
import fr.paris.lutece.plugins.workflow.utils.WorkflowUtils;
import fr.paris.lutece.plugins.workflow.web.task.NoFormTaskComponent;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.plugins.workflowcore.service.task.ITask;
import fr.paris.lutece.portal.service.i18n.I18nService;
import fr.paris.lutece.portal.service.plugin.Plugin;
import fr.paris.lutece.portal.service.plugin.PluginService;
import fr.paris.lutece.portal.service.template.AppTemplateService;
import fr.paris.lutece.util.ReferenceItem;
import fr.paris.lutece.util.ReferenceList;
import fr.paris.lutece.util.html.HtmlTemplate;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.inject.Named;

import javax.servlet.http.HttpServletRequest;


/**
 *
 * CreatePDFTaskComponent
 *
 */
public class CreatePDFTaskComponent extends NoFormTaskComponent
{
    // MARKS
    private static final String MARKER_TASK_FORMSPDF_CONFIG = "task_config";

    // TEMPLATES
    private static final String TEMPLATE_TASK_CREATE_PDF = "admin/plugins/workflow/modules/formspdf/task_create_pdf_config.html";

    // PROPERTIES
    private static final String PROPERTY_LABEL_DEFAULT = "module.workflow.formspdf.task_create_pdf_config.label.default";

    // SERVICES
    @Inject
    @Named( FormsPDFConstants.BEAN_CREATE_PDF_CONFIG_SERVICE )
    private ITaskConfigService _taskCreatePDFConfigService;

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayConfigForm( HttpServletRequest request, Locale locale, ITask task )
    {
        Map<String, Object> model = new HashMap<String, Object>(  );
        String strIdTask = request.getParameter( FormsPDFConstants.PARAMETER_ID_TASK );

        int nIdForms;

        if ( StringUtils.isNotBlank( request.getParameter( FormsPDFConstants.PARAMETER_ID_FORM ) ) )
        {
            nIdForms = Integer.parseInt( request.getParameter( 
                        FormsPDFConstants.PARAMETER_ID_FORM ) );
        }
        else
        {
            nIdForms = -1;
        }

        if ( StringUtils.isNotBlank( strIdTask ) )
        {
            TaskCreatePDFConfig taskCreatePDFConfig = _taskCreatePDFConfigService.findByPrimaryKey( Integer.parseInt( 
                        strIdTask ) );

            if ( taskCreatePDFConfig != null )
            {
                model.put( MARKER_TASK_FORMSPDF_CONFIG, taskCreatePDFConfig );
                nIdForms = taskCreatePDFConfig.getIdForm(  );
            }
        }

        model.put( FormsPDFConstants.MARK_FORMS_LIST, FormHome.getFormsReferenceList( ) );
        model.put( FormsPDFConstants.MARK_LIST_ENTRIES_URL, getListEntriesUrl( nIdForms, request ) );
        model.put( FormsPDFConstants.MARK_LIST_CONFIG_PDF, getListConfigPDF( nIdForms, locale ) );

        HtmlTemplate template = AppTemplateService.getTemplate( TEMPLATE_TASK_CREATE_PDF, locale, model );

        return template.getHtml(  );
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getDisplayTaskInformation( int nIdHistory, HttpServletRequest request, Locale locale, ITask task )
    {
        return null;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getTaskInformationXml( int nIdHistory, HttpServletRequest request, Locale locale, ITask task )
    {
        return null;
    }

    /**
     * Method to get forms entries list
     * @param nIdForms id forms
     * @param request request
     * @return ReferenceList entries list
     */
    private static ReferenceList getListEntriesUrl( int nIdForms, HttpServletRequest request )
    {
        if ( nIdForms != -1 )
        {
            List<Question> listQuestion = QuestionHome.getListQuestionByIdForm( nIdForms );
            ReferenceList referenceList = new ReferenceList(  );

            for ( Question question : listQuestion )
            {
                if ( question.getEntry().getEntryType(  ).getComment(  ) )
                {
                    continue;
                }

                if ( question.getEntry().getEntryType(  ).getGroup(  ) )
                {
                    if ( question.getEntry().getChildren(  ) != null )
                    {
                        for ( Entry child : question.getEntry().getChildren(  ) )
                        {
                            if ( child.getEntryType(  ).getComment(  ) )
                            {
                                continue;
                            }

                            ReferenceItem referenceItem = new ReferenceItem(  );
                            referenceItem.setCode( String.valueOf( child.getIdEntry(  ) ) );
                            referenceItem.setName( child.getTitle(  ) );
                            referenceList.add( referenceItem );
                        }
                    }
                }
                else
                {
                    ReferenceItem referenceItem = new ReferenceItem(  );
                    referenceItem.setCode( String.valueOf( question.getEntry().getIdEntry(  ) ) );
                    referenceItem.setName( question.getEntry().getTitle(  ) );
                    referenceList.add( referenceItem );
                }
            }

            return referenceList;
        }
        else
        {
            return null;
        }
    }

    /**
     * Method to get list of config, by id forms
     * @param nIdForms id forms
     * @param locale the locale
     * @return ReferenceList list of config
     */
    private static ReferenceList getListConfigPDF( int nIdForms, Locale locale )
    {
        Plugin pluginFormsPDFProducer = PluginService.getPlugin( FormsDocumentProducerPlugin.PLUGIN_NAME );
        List<ConfigProducer> listConfigProducer = ConfigProducerHome.loadListProducerConfig(pluginFormsPDFProducer, nIdForms );

        //Keep only the type PDF
        listConfigProducer.stream()
                .filter( config -> config.getType().equals( DocumentType.PDF.toString()))
                .collect( Collectors.toList( ) );
        
        ReferenceList referenceList = new ReferenceList(  );
        referenceList.addItem( WorkflowUtils.CONSTANT_ID_NULL,
            I18nService.getLocalizedString( PROPERTY_LABEL_DEFAULT, locale ) );

        for ( ConfigProducer configProducer : listConfigProducer )
        {
            ReferenceItem referenceItem = new ReferenceItem(  );
            referenceItem.setCode( String.valueOf( configProducer.getIdProducerConfig(  ) ) );
            referenceItem.setName( configProducer.getName(  ) );
            referenceList.add( referenceItem );
        }

        return referenceList;
    }
}
