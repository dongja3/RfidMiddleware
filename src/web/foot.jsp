<table width="100%" border="0" cellspacing="0" cellpadding="0">
  <tr height="41" valign="top" bgcolor="#444444">
    <td></td>
    <td>
      <div align="right">
        <br>
        <span class="foot-bgcolor">
          Copyright &copy;
          <a href="http://www.ubipass.com" target="_blank" class="white"><font color="#FFFFFF">Ubipass.com
            </font>
          </a>
          &nbsp;&nbsp;
          <fmt:message key="foot.versionNumber"/>
          <font color="#FFFFFF">
<%
            out.print(com.ubipass.middleware.ems.EMSUtil.getVersionNum());
%>
          </font>
          &nbsp;&nbsp;
          <fmt:message key="foot.buildNumber"/>
          #
          <font color="#FFFFFF">
<%
            out.print(com.ubipass.middleware.ems.EMSUtil.getBuildNum());
%>
          </font>
          &nbsp;&nbsp;&nbsp;&nbsp;
        </span>
      </div>
    </td>
  </tr>
</table>
