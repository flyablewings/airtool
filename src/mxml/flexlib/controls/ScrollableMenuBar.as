/*
Copyright (c) 2007 FlexLib Contributors.  See:
    http://code.google.com/p/flexlib/wiki/ProjectContributors

Permission is hereby granted, free of charge, to any person obtaining a copy of
this software and associated documentation files (the "Software"), to deal in
the Software without restriction, including without limitation the rights to
use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
of the Software, and to permit persons to whom the Software is furnished to do
so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
*/

package flexlib.controls
{
	import flash.events.Event;
	
	import mx.controls.Menu;
	import mx.controls.MenuBar;
	import mx.controls.menuClasses.IMenuBarItemRenderer;
	import mx.core.IFactory;
	import mx.core.mx_internal;
	import mx.events.MenuEvent;
	import mx.styles.CSSStyleDeclaration;
	import mx.styles.StyleManager;
	
	use namespace mx_internal;

	[IconFile("ScrollableMenu.png")]
	
	/**
	 * ScrollableMenuBar is an extension of MenuBar that uses <code>flexlib.controls.ScrollableMenu</code>
	 * instead of using the original <controls>mx.controls.Menu</controls>. This allows us to specify a maxHeight for the 
	 * ScrollableMenuBar and that maxHeight will be used to determine the maxHeight for all the
	 * menus that the component generates.
	 * 
	 * <p>We only had to override the getMenuAt method to make it generate a ScrollableMenu. In order to 
	 * set the event listeners of the newly created ScrollableMenu, the eventHandler method (which was 
	 * a private method of MenuBar) was duplicated in this class.</p>
	 * 
	 * @see mx.controls.MenuBar
	 */
	public class ScrollableMenuBar extends MenuBar
	{
		
		/**
		 * Constructor
		 */
		public function ScrollableMenuBar()
		{
			super();			
		}
		
		protected var _menuWidth:int=0;
		public function set menuWidth(value:int):void
		{
			this._menuWidth = value;
		}		
		public function get menuWidth():int
		{
			return this._menuWidth;
		}
		
		/**
		 * @private
		 * 
		 * We need to add verticalScrolLPolicy and arrowScrollPolicy to this class. The normal MenuBar didn't have
		 * any scrolling before, so it didn't even have the verticalScrollPolicy variable.
		 */
		private var _verticalScrollPolicy:String;
		
		/**
		 * Specifys the vertical scrolling policy for this control. 
		 * 
		 * @see mx.core.ScrollPolicy
		 */
		public function get verticalScrollPolicy():String {
			return this._verticalScrollPolicy;
		}
		
		/**
		 * @private
		 */
		public function set verticalScrollPolicy(value:String):void 
		{
			if (value!=null)
			{
				var newPolicy:String = value.toLowerCase();
		        if (_verticalScrollPolicy != newPolicy)
		        {
		            _verticalScrollPolicy = newPolicy;
		        }
	        	invalidateDisplayList();
	  		}
		}
		
		
		/**
		 * @private
		 */
		private var _arrowScrollPolicy:String;
    	
    	/**
    	 * The policy to show the up and down arrows at the top and bottom of the menu
    	 * control. 
    	 * 
    	 * <p>Possible values are the same as verticalScrollPolicy and can be 
    	 * ScrollPolicy.AUTO, ScrollPolicy.ON and ScrollPolicy.OFF. ScrollPolicy.ON 
    	 * shouldn't be used since it obstructs the menu items at the top and bottom
    	 * of the list. Why did I allow it? I don't know.</p>
    	 */
    	public function get arrowScrollPolicy():String {
			return this._arrowScrollPolicy;
		}
		
    	/**
		 * @private
		 */
	    public function set arrowScrollPolicy(value:String):void 
	    {
	    	if (value!=null)
			{
				var newPolicy:String = value.toLowerCase();	
			    if (_arrowScrollPolicy != newPolicy)
			    {
			    	_arrowScrollPolicy = newPolicy;
			    }
		        invalidateDisplayList();
		 	}
		}
			
		/**
		 * Overriding getMenuAt because the original method in
		 * MenuBar creates a new Menu object. We need to create a new ScrollableMenu
		 * instead, so we're forced to override this entire method.
		 */
		override public function getMenuAt(index:int):Menu
	    {
	    	/* In our subclass we don't have access to the variable dataProivderChanged */
	        //if (dataProviderChanged)
	            //commitProperties();
	
	        var item:IMenuBarItemRenderer = menuBarItems[index];
	      
	        var mdp:Object = item.data;
	        //var menu:ScrollableArrowMenu = menus[index];
	        var menu:* = menus[index] as ScrollableArrowMenu;
	
	        if (menu == null)
	        {
	        	/* Here's where we use ScrollableMenu instead of Menu */
	            menu = new ScrollableArrowMenu();
	            if (menuItemRenderer!=null)
	            {
	            	menu.itemRenderer= menuItemRenderer.newInstance();
	            	if (menu.itemRenderer.hasOwnProperty("menuWidth"))
	            	{
	            		menu.itemRenderer.menuWidth = this.menuWidth;
	            	}
	            }
	            menu.verticalScrollPolicy = this.verticalScrollPolicy;
	            menu.arrowScrollPolicy = this.arrowScrollPolicy;
	            
	            /* And we set the maxHeight for the menu to be the same as 
	             * the maxHeight for this ScrollableMenuBar */
	            menu.maxHeight = this.maxHeight;
	            
	            menu.showRoot = false;
	            menu.styleName = this;
	            
	            var menuStyleName:Object = getStyle("menuStyleName");
	            if (menuStyleName)
	            {
	                var styleDecl:CSSStyleDeclaration =
	                    StyleManager.getStyleDeclaration("." + menuStyleName);
	                if (styleDecl)
	                    menu.styleDeclaration = styleDecl;
	            }
	            
	            menu.sourceMenuBar = this;
	            menu.owner = this;
	            menu.addEventListener("menuHide", eventHandler);
	            menu.addEventListener("itemRollOver", eventHandler);
	            menu.addEventListener("itemRollOut", eventHandler);
	            menu.addEventListener("menuShow", eventHandler);
	            menu.addEventListener("itemClick", eventHandler);
	            menu.addEventListener("change", eventHandler);
	            
	            /* In the original MenuBar class, these lines use internal private variable
	             * to set the properties of the menu. But each of the variables that is 
	             * private also has a public getter method, so I just changed it to use that
	             * instead. 
	             */
	            menu.iconField = this.iconField;
	            menu.labelField = this.labelField;
	            menu.labelFunction = labelFunction;
	            menu.dataDescriptor = _dataDescriptor;
	            menu.invalidateSize();
	
	            menus[index] = menu;
	            menu.sourceMenuBarItem = item; // menu needs this for a hitTest when clicking outside menu area
	            Menu.popUpMenu(menu, null, mdp);
	        }
	        
	        if(menu.maxHeight != this.maxHeight) {
	        	menu.maxHeight = this.maxHeight;
	        }
	        
	        if(menu.verticalScrollPolicy != this.verticalScrollPolicy) {
	        	menu.verticalScrollPolicy = this.verticalScrollPolicy;
	        }
	        
	        if(menu.arrowScrollPolicy != this.arrowScrollPolicy) {
	        	menu.arrowScrollPolicy = this.arrowScrollPolicy;
	        }
	
			/* This now calls the getMenuAt method of MenuBar, which doesn't
			 * do much since we've already created the menu. But it should call 
			 * commitProperties if needed (see the beginning of this function)
			 */
	        return super.getMenuAt(index);
	    
	   	}
	    
	    /**
	    * @private
	    * 
	    * This is a copy of the eventHandler method from MenuBar. It had to be duplicated here
	    * so I could set the listeners in the getPopUp method above. There wasn't much in it, and 
	    * nothing that specifically needed access to private variables or methods of MenuBar.
	    */
	    private function eventHandler(event:Event):void
	    {
	        //these events come from the menu's themselves. 
	        //we'll redispatch all of them. 
	        if (event is MenuEvent) 
	        {
	            var t:String = event.type;
	    
	    		var openMenuIndex:Number = this.selectedIndex;
	    		
	            if (event.type == MenuEvent.MENU_HIDE && 
	                MenuEvent(event).menu == menus[openMenuIndex])
	            {
	                menuBarItems[openMenuIndex].menuBarItemState = "itemUpSkin";
	                openMenuIndex = -1;
	                dispatchEvent(event as MenuEvent);
	            }
	            else
	                dispatchEvent(event);
	        }
	    }
	    
	    /**
	     *  Returns the class for an icon, if any, for a data item,  
	     *  based on the <code>iconField</code> property.
	     *  The field in the item can return a string as long as that
	     *  string represents the name of a class in the application.
	     *  The field in the item can also be a string that is the name
	     *  of a variable in the document that holds the class for
	     *  the icon.
	     *  
	     *  @param data The item from which to extract the icon class
	     *  @return The icon for the item, as a class reference or 
	     *  <code>null</code> if none.
	     */
	    override public function itemToIcon(data:Object):Class
	    {
	        if (data == null)
	        {
	            return null;
	        }
	        var iconClass:Class;
	        var icon:*;
	
	        if (data is XML)
	        {
	            try
	            {
	                if (data[iconField].length() != 0)
	                {
	                   icon = String(data[iconField]);
	                   if (icon != null)
	                   {
	                       iconClass =
	                            Class(systemManager.getDefinitionByName(icon));
	                       if (iconClass)
	                           return iconClass;
	
	                       return document[icon];
	                   }
	                }
	            }
	            catch(e:Error)
	            {
	            }
	        }
	
	        else if (data is Object)
	        {
	            try
	            {
	                if (data[iconField] != null)
	                {
	                    if (data[iconField] is Class)
	                    {
	                        return data[iconField];
	                    }
	                    if (data[iconField] is String)
	                    {
	                        iconClass = Class(systemManager.getDefinitionByName(
	                                                data[iconField]));
	                        if (iconClass)
	                        {
	                            return iconClass;
	                        }
	                        return document[data[iconField]];
	                    }
	                }
	            }
	            catch(e:Error)
	            {
	            }
	        }
	
	        return null;
	    }
	    
	    
	    /**
	     *  @private
	     *  Storage for the menuBarItemRenderer property.
	     */
	    protected var _menuItemRenderer:IFactory;
	    public function get menuItemRenderer():IFactory
	    {
	        return this._menuItemRenderer;
	    }
	
	    /**
	     * @private 
	     */
	    public function set menuItemRenderer(value:IFactory):void
	    {
	        if (_menuItemRenderer != value)
	        {
	            this._menuItemRenderer = value;
	        }
	    }
	}
}