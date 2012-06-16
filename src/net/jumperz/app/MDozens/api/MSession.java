package net.jumperz.app.MDozens.api;

import net.jumperz.app.MDozens.*;
import net.jumperz.util.MStreamUtil;
import net.arnx.jsonic.*;
import java.util.*;
import java.net.*;
import java.io.*;

public class MSession
extends MAbstractLogAgent
implements MConstants
{
private String user;
private String apiKey;
//--------------------------------------------------------------------------------
public MSession( String user, String apiKey )
{
this.user = user;
this.apiKey = apiKey;
}
//--------------------------------------------------------------------------------
private Map getApiResponse( String apiPath, Map header )
throws IOException
{
URL url = new URL( "https://dozens.jp" + apiPath );
URLConnection conn = url.openConnection();
Iterator p = header.keySet().iterator();
while( p.hasNext() )
	{
	String key = ( String )p.next();
	String value = ( String )header.get( key );
	conn.addRequestProperty( key, value );
	debug( key + ":" + value );
	}

Map result = ( Map )JSON.decode( MStreamUtil.streamToString( conn.getInputStream() ) );
return result;
}
//--------------------------------------------------------------------------------
public void init()
throws IOException
{
Map header = new HashMap();
header.put( "X-Auth-User", user );
header.put( "X-Auth-Key", apiKey );

Map result = getApiResponse( "/api/authorize.json", header );
debug( result );
}
//--------------------------------------------------------------------------------
}