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
package fr.paris.lutece.plugins.workflow.modules.formspdf.business;

import fr.paris.lutece.plugins.workflow.modules.formspdf.utils.FormsPDFConstants;
import fr.paris.lutece.plugins.workflowcore.service.config.ITaskConfigService;
import fr.paris.lutece.portal.service.spring.SpringContextService;
import fr.paris.lutece.test.LuteceTestCase;

public class TaskCreatePDFConfigBusinessTest extends LuteceTestCase
{
    private ITaskConfigService _taskCreatePDFConfigService;

    @Override
    protected void setUp( ) throws Exception
    {
        super.setUp( );
        _taskCreatePDFConfigService = SpringContextService.getBean( FormsPDFConstants.BEAN_CREATE_PDF_CONFIG_SERVICE );
    }
    
    public void testCRUD( )
    {
        TaskCreatePDFConfig config = new TaskCreatePDFConfig( );
        config.setIdTask( 1 );
        config.setIdForm( 2 );
        config.setIdConfig( 3 );
        config.setIdQuestionUrlPDF( 4 );

        _taskCreatePDFConfigService.create( config );

        TaskCreatePDFConfig loaded = _taskCreatePDFConfigService.findByPrimaryKey( config.getIdTask( ) );
        assertEquals( config.getIdForm( ), loaded.getIdForm( ) );
        assertEquals( config.getIdConfig( ), loaded.getIdConfig( ) );
        assertEquals( config.getIdQuestionUrlPDF( ), loaded.getIdQuestionUrlPDF( ) );

        config.setIdQuestionUrlPDF( 5 );
        _taskCreatePDFConfigService.update( config );

        loaded = _taskCreatePDFConfigService.findByPrimaryKey( config.getIdTask( ) );
        assertEquals( config.getIdForm( ), loaded.getIdForm( ) );
        assertEquals( config.getIdConfig( ), loaded.getIdConfig( ) );
        assertEquals( config.getIdQuestionUrlPDF( ), loaded.getIdQuestionUrlPDF( ) );

        _taskCreatePDFConfigService.remove( config.getIdTask( ) );

        loaded = _taskCreatePDFConfigService.findByPrimaryKey( config.getIdTask( ) );
        assertNull( loaded );
    }
}
