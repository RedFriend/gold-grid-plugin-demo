var str = '';
var copyright="深圳市中级人民法院[专用];V5.0S0xGAAEAAAAAAAAAEAAAAFkBAABgAQAALAAAALZ9u2YR1wyAL2Szqa46qnakQjE0TqBRga9ie83BhD+uS41pQspgHaBL36CrqFOxgm7BWxoF7MdzHNr2VJtAAl43w3tusfzCcqM8h/Sq2vkviB+qb1cxv86kDbit0cNndxbo85KuMMvIvwtdU0uVxHRKjcuCtqSYjlyLIBVzQ4Pu5P+kB4XnjYv5xYOvIP+EjrLZL1bSN15+KmjutQIVBjsH2Lz5aiVHvm8c7ZDgvwB2pDvKKOVUnv1gK2E1H4jk49ocMaDQhNkc78vjKQ/SZS8e+StJgmOebohT8K7YGF1D55wV7tARJpP/s8/UwycHcF1cJlUd/nkt/2lL8t8fjyJv1Dp732/l3bge9nxSnSEWlpHQStzuyIBM1vBVEupKmuAzBEKtVXwFgANTvU7QWqWcHiyEzNCrVe9UH+rrzYbjBQzVEjcyiy4IuG566D3jPE3RqB7TCk5YTQ81IB7/EcszTfzuXGUp3bwFWdiXzOzl";

str += '<object id="WebOffice2015" ';
str += ' width="100%"';
str += ' height="100%"';
if ((window.ActiveXObject!=undefined) || (window.ActiveXObject!=null) ||"ActiveXObject" in window)
{
	str += ' CLASSID="CLSID:D89F482C-5045-4DB5-8C53-D2C9EE71D025"  codebase="iWebOffice2015.cab#version=12,2,0,382"';
	str += '>';
	str += '<param name="Copyright" value="' + copyright + '">';
}
else
{
	str += ' progid="Kinggrid.iWebOffice"';
	//str += ' type="application/iwebplugin"';
	str += ' type="application/kg-activex"';
	str += ' OnCommand="OnCommand"';
	str += ' OnReady="OnReady"';
	str += ' OnOLECommand="OnOLECommand"';
	str += ' OnExecuteScripted="OnExecuteScripted"';
	str += ' OnQuit="OnQuit"';
	str += ' OnSendStart="OnSendStart"';
	str += ' OnSending="OnSending"';
	str += ' OnSendEnd="OnSendEnd"';
	str += ' OnRecvStart="OnRecvStart"';
	str += ' OnRecving="OnRecving"';
	str += ' OnRecvEnd="OnRecvEnd"';
	str += ' OnRightClickedWhenAnnotate="OnRightClickedWhenAnnotate"';
	str += ' OnFullSizeBefore="OnFullSizeBefore"';
	str += ' OnFullSizeAfter="OnFullSizeAfter"';	
	str += ' Copyright="' + copyright + '"';
	str += '>';
}
str += '</object>';
document.write(str); 