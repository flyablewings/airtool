package com.utils
{
	import com.objects.EntityPoint;
	
	import flash.geom.Point;
	
	public class EntityDisplayUtil
	{
		public static function localTolocalHadScroll(entityPoint:EntityPoint):Point{
			var i:int = entityPoint.attributeIndex;
			//Item count is being hide
			var h:int = entityPoint.entity.verticalScrollPosition;
			if(entityPoint.entity.isVerticalScrollBarShowing()){
				var p:int = entityPoint.entity.rowCount;
				var blnIsbeHide:Boolean = true;
				var blnIsbeHideTop:Boolean = true;
				/* if( h <= i && i <= p + h -1 ){
					return new Point(0, entityPoint.entity.rowHeight*(i - h) + entityPoint.entity.rowHeight/2);
				} */
				if( h > i ){ // Be hiding top
					return new Point(entityPoint.attribute.x, -5);
				}
				if( i > p + h -1){ // be hiding bottom
					return new Point(entityPoint.attribute.x, entityPoint.entity.height);
				}
				return new Point(0, entityPoint.entity.rowHeight*(i - h) + entityPoint.entity.rowHeight/2);
			}
			return new Point(0, entityPoint.entity.rowHeight*(i - h) + entityPoint.entity.rowHeight/2);
		}
	}
}