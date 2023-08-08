package com.hokukou.report;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRPrintText;
import net.sf.jasperreports.engine.export.JRExporterGridCell;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.data.BooleanTextValue;
import net.sf.jasperreports.engine.export.data.DateTextValue;
import net.sf.jasperreports.engine.export.data.NumberTextValue;
import net.sf.jasperreports.engine.export.data.StringTextValue;
import net.sf.jasperreports.engine.export.data.TextValue;
import net.sf.jasperreports.engine.export.data.TextValueHandler;
import net.sf.jasperreports.engine.util.JRStyledText;

/**
 * update history:
 * 2007/11/12:	EXCEL保存時、あるセルのデータがEXCEL計算式の対象となっていないエラーの修正
 * 2008/01/22:  EXCEL保存時、カンマ(Comma)編集された数値データがEXCEL計算の対象となっていないエラーの修正
 */
public class HKJRXlsExporter extends JRXlsExporter{

	private boolean isFormatedNum(String strValue){
		if(strValue.indexOf(",") > -1){
			try{
				new Double(dataConv(strValue));
			}catch(Exception e){
				return false;
			}
			return true;
		}else{
			return false;
		}
	}
	
	private String dataConv(String strValue){
		return strValue.replace(",", "").trim();
	}
	
	protected void createTextCell(final JRPrintText textElement, final JRExporterGridCell gridCell, final int colIndex, final int rowIndex, JRStyledText styledText, final StyleInfo baseStyle) throws JRException
	{
		String textStr = styledText.getText();

		if (isDetectCellType)
		{
			TextValue value = getTextValue(textElement, textStr);
			value.handle(new TextValueHandler()
			{
				public void handle(StringTextValue textValue)
				{
					//is styled text 
					if(textElement.isStyledText()){
						baseStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("@"));
					}else{
						//是否为一个使用了千位分隔的字符串 add by zhangfei  2008/01/21
						if(isFormatedNum(textValue.getText())){
							BigDecimal bg = new BigDecimal(dataConv(textValue.getText()));
							java.text.DecimalFormat decFormat = new DecimalFormat();
							decFormat.setGroupingUsed(true);
							decFormat.setMaximumFractionDigits(bg.scale());
							decFormat.setMinimumFractionDigits(bg.scale());
							
							baseStyle.setDataFormat(
									dataFormat.getFormat(
											decFormat.toPattern()
										)
									);
						//add end	
						}else{
							baseStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("General"));
						}
					}
					

					HSSFCellStyle cellStyle = initCreateCell(gridCell, colIndex, rowIndex, baseStyle);
					
					if (textValue.getText() == null || textValue.getText().equals(""))
					{
						cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
					}
					else
					{
						if(!textElement.isStyledText()){
							try{
								cell.setCellValue(new Double(dataConv(textValue.getText())));
							}catch(Exception e){
								setStringCellValue(textValue.getText());
							}
							
						}else{
							setStringCellValue(textValue.getText());
						}
					}
					
					//setStringCellValue(textValue.getText());
					endCreateCell(cellStyle);
				}

				public void handle(NumberTextValue textValue)
				{
					if (textValue.getPattern() != null)
					{
						baseStyle.setDataFormat(
							dataFormat.getFormat(
								getConvertedPattern(textValue.getPattern())
								)
							);
					}

					HSSFCellStyle cellStyle = initCreateCell(gridCell, colIndex, rowIndex, baseStyle);

					if (textValue.getValue() == null || textValue.equals(""))
					{
						cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
					}
					else
					{
						cell.setCellValue(textValue.getValue().doubleValue());
					}
					endCreateCell(cellStyle);
				}

				public void handle(DateTextValue textValue)
				{
					baseStyle.setDataFormat(
						dataFormat.getFormat(
							getConvertedPattern(textValue.getPattern())
							)
						);
					HSSFCellStyle cellStyle = initCreateCell(gridCell, colIndex, rowIndex, baseStyle);
					if (textValue.getValue() == null)
					{
						cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
					}
					else
					{
						cell.setCellValue(textValue.getValue());
					}
					endCreateCell(cellStyle);
				}

				public void handle(BooleanTextValue textValue)
				{
					HSSFCellStyle cellStyle = initCreateCell(gridCell, colIndex, rowIndex, baseStyle);
					if (textValue.getValue() == null)
					{
						cell.setCellType(HSSFCell.CELL_TYPE_BLANK);
					}
					else
					{
						cell.setCellValue(textValue.getValue().booleanValue());
					}
					endCreateCell(cellStyle);
				}
				
				/**
				 * This method is intended to modify a given format pattern so to include 
				 * only the accepted proprietary format characters. The resulted pattern 
				 * will possibly truncate the original pattern
				 * @param pattern
				 * @return pattern converted to accepted proprietary formats
				 */
				private String getConvertedPattern(String pattern)
				{
					if (formatPatternsMap != null && formatPatternsMap.containsKey(pattern))
					{
						return (String) formatPatternsMap.get(pattern);
					}
					return pattern;
				}

			});
		}
		else if (isAutoDetectCellType)
		{
			HSSFCellStyle cellStyle = initCreateCell(gridCell, colIndex, rowIndex, baseStyle);
			try
			{
				cell.setCellValue(Double.parseDouble(textStr));
			}
			catch(NumberFormatException e)
			{
				setStringCellValue(textStr);
			}
			endCreateCell(cellStyle);
		}
		else
		{
			HSSFCellStyle cellStyle = initCreateCell(gridCell, colIndex, rowIndex, baseStyle);
			setStringCellValue(textStr);
			endCreateCell(cellStyle);
		}
	}

}
