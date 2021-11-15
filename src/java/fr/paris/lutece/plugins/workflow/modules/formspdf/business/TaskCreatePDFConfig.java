/*
 * Copyright (c) 2002-2021, City of Paris
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
package fr.paris.lutece.plugins.workflow.modules.formspdf.business;

import fr.paris.lutece.plugins.workflowcore.business.config.TaskConfig;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * TaskCreatePDFConfig
 *
 */
public class TaskCreatePDFConfig extends TaskConfig
{
    @NotNull
    @Min( 1 )
    private int _nIdForm;
    private int _nIdConfig;
    @NotNull
    @Min( 1 )
    private int _nIdQuestionUrlPDF;

    /**
     * @return the IdForm
     */
    public int getIdForm( )
    {
        return _nIdForm;
    }

    /**
     * @param nIdForm
     *            the IdForms to set
     */
    public void setIdForm( int nIdForm )
    {
        _nIdForm = nIdForm;
    }

    /**
     * @return the _nIdConfig
     */
    public int getIdConfig( )
    {
        return _nIdConfig;
    }

    /**
     * @param nIdConfig
     *            the _nIdConfig to set
     */
    public void setIdConfig( int nIdConfig )
    {
        _nIdConfig = nIdConfig;
    }

    /**
     * @return the _nIdQuestionUrlPDF
     */
    public int getIdQuestionUrlPDF( )
    {
        return _nIdQuestionUrlPDF;
    }

    /**
     * @param nIdQuestionUrlPDF
     *            the _nIdQuestionUrlPDF to set
     */
    public void setIdQuestionUrlPDF( int nIdQuestionUrlPDF )
    {
        _nIdQuestionUrlPDF = nIdQuestionUrlPDF;
    }
}
