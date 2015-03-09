// $Source: D:/cvsrepository/middleware/src/java/com/ubipass/middleware/jdbc/po/PluginsPO.java,v $
// LastModified By: $Author: liujiaqi $
 // $Date: 2005/01/18 07:31:34 $
package com.ubipass.middleware.jdbc.po;

/**
* * PluginsPO class 
* 
* @version $Revision: 1.1 $ 
* @author LiuJiaqi 
* @author $Author: liujiaqi $ 
*/
public class PluginsPO {
	private int id;
	private String pluginName;
	private String pluginClass;
	/**
	 * @return Returns the id.
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id The id to set.
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return Returns the pluginClass.
	 */
	public String getPluginClass() {
		return pluginClass;
	}
	/**
	 * @param pluginClass The pluginClass to set.
	 */
	public void setPluginClass(String pluginClass) {
		this.pluginClass = pluginClass;
	}
	/**
	 * @return Returns the pluginName.
	 */
	public String getPluginName() {
		return pluginName;
	}
	/**
	 * @param pluginName The pluginName to set.
	 */
	public void setPluginName(String pluginName) {
		this.pluginName = pluginName;
	}
}
