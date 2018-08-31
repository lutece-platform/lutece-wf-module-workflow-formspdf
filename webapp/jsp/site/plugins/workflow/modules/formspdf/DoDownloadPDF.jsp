<jsp:useBean id="doDownloadPDF" scope="session" class="fr.paris.lutece.plugins.workflow.modules.formspdf.utils.DoDownloadPDF" /><%
	 doDownloadPDF.doDownloadFile(request,response);
%>
