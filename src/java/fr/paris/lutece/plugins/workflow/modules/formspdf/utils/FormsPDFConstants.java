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

/**
 * FormsPDFConstants
 *
 */
public final class FormsPDFConstants
{
    // BEANS
    public static final String BEAN_CREATE_PDF_CONFIG_SERVICE = "workflow-formspdf.taskCreatePDFConfigService";

    // PARAMETERS
    public static final String PARAMETER_ID_FORM = "id_form";
    public static final String PARAMETER_ID_FORM_RESPONSE = "id_form_response";
    public static final String PARAM_ITEM_KEY = "item_key";
    public static final String PROPERTY_WEBAPP_WORKFLOW_FORMSPDF_URL = "workflow.formspdf.url.download.pdf";
    public static final String URL_DOWNLOAD_PDF = "servlet/plugins/formspdf/downloadpdf";
    public static final String PARAMETER_ID_TASK = "id_task";
    public static final String MARK_FORMS_LIST = "list_forms";
    public static final String MARK_LIST_ENTRIES_URL = "list_question_url";
    public static final String MARK_LIST_CONFIG_PDF = "list_config_pdf";
    public static final String TYPE_CONFIG_PDF = "PDF";
    public static final String PROPERTY_LUTECE_BASE_URL = "lutece.base.url";
    public static final String PROPERTY_LUTECE_PROD_URL = "lutece.prod.url";
    public static final String SLASH = "/";

    /**
     * Constructor
     */
    private FormsPDFConstants( )
    {
    }
}
