/** Name of the JPO    : ${CLASSNAME}
 ** Developed by    : Matrixone 
 ** Client            : WMS
 ** Description        : The purpose of this JPO is to create a Ranges for attributes
 ** Revision Log:
 ** -----------------------------------------------------------------
 ** Author                    Modified Date                History
 ** -----------------------------------------------------------------

 ** -----------------------------------------------------------------
 **/
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.matrixone.apps.domain.DomainConstants;
import com.matrixone.apps.domain.DomainObject;
import com.matrixone.apps.domain.DomainRelationship;
import com.matrixone.apps.domain.util.CacheUtil;
import com.matrixone.apps.domain.util.EnoviaResourceBundle;
import com.matrixone.apps.domain.util.MapList;
import com.matrixone.apps.domain.util.MqlUtil;
import com.matrixone.apps.domain.util.mxAttr;

import matrix.db.Context;
import matrix.db.JPO;
import matrix.util.StringList;

/**
 * The purpose of this JPO is to create a Range for attributes.
 * @author WMS
 * @version R418 - Copyright (c) 1993-2016 Dassault Systems.
 */
public class WMSRange_mxJPO extends WMSConstants_mxJPO
{
    /**
     * Create a new ${CLASS:MarketingFeature} object from a given id.
     *
     * @param context the eMatrix <code>Context</code> object
     * @param args holds no arguments.
     * @throws Exception if the operation fails
     * @author CHiPS
     * @since R418
     */

    public WMSRange_mxJPO (Context context, String[] args)
        throws Exception
    {
       super(context,args);
    }
	@com.matrixone.apps.framework.ui.CellRangeJPOCallable
	public Object getRangeForRateEscalation(Context context, String[] args) throws Exception
	{
		Map returnMap = new HashMap(2);
		try 
		{
			StringList slImportancelist = new StringList();	      
			slImportancelist.addElement("Yes");
			slImportancelist.addElement("No");
			returnMap.put("field_choices", slImportancelist);
			returnMap.put("field_display_choices", slImportancelist);     
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			throw ex;
		}
		return returnMap;
	}
	
	  @com.matrixone.apps.framework.ui.CellRangeJPOCallable
	  public Map getUWDRage(Context context, String[] args) throws Exception{
	        try {
	                String strUWDValue = EnoviaResourceBundle.getProperty(context, "WMS",
	                ("WMS.Table.Label.WMSUWDValues"), context.getLocale().US);
	                StringList slUWDDisplay     = new StringList();
	                StringList slUWDActual      = new StringList();
	                String strPart1             = "";
	                String strPart2             = "";
	                String [] arrayValue        = strUWDValue.split(",");
	                ArrayList<Double> alValue	=   new ArrayList<Double>();
	                HashMap<String, String> KeyValue = new HashMap<String, String>();
	                
	                for (int i=0;i< arrayValue.length ; i++ )
	                {
	                    String [] strDisAndActual = arrayValue[i].split(":");
	                    KeyValue.put(strDisAndActual[1], strDisAndActual[0]);
	                    alValue.add(Double.parseDouble(strDisAndActual[1]));
	                    //slUWDDisplay.add(strDisAndActual[0] + " - "+ strDisAndActual[1]);
	                   slUWDActual.add(strDisAndActual[0] + " - "+ strDisAndActual[1]);   
	                }
		            slUWDActual.add(" "); 
	                Map mapUWD     = new HashMap();
	                mapUWD.put("field_choices",slUWDActual );
	                mapUWD.put("field_display_choices", slUWDActual);
	                return mapUWD;
	            } catch (Exception ex) {
	                ex.printStackTrace();
	                throw ex;
	            }
	        }
	  
	  /** 
	     * Method will Add Ranges for Is Deduction
	     * @param context - the eMatrix <code>Context</code> object
	     * @param String array args containing the programMap
	     * @return - Map containing the ranges 
	     * @throws Exception if the operation fails
	     * @author CHiPS
	     * @since 418
	     */
	  @com.matrixone.apps.framework.ui.CellRangeJPOCallable
	    public Map getIsDeduction(Context context, String[] args) throws Exception{

	        try {
	            StringList slRange          = mxAttr.getChoices( context,ATTRIBUTE_WMS_IS_DEDUCTION );
	            Map newIssueRange = new HashMap();
	            newIssueRange.put("field_choices", slRange);
	            newIssueRange.put("field_display_choices", slRange);
	            return newIssueRange;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
	}
	    
	  
	    /** 
	     * Method will return ApprovalTemplates with Value MB Approval 
	     * @param context - the eMatrix <code>Context</code> object
	     * @param String array args containing the programMap
	     * @return - Map containing the ranges 
	     * @throws Exception if the operation fails
	     * @author CHiPS
	     * @since 418
	     */
	    @com.matrixone.apps.framework.ui.CellRangeJPOCallable
	    public Map getApplicableATemplates(Context context, String[] args) throws Exception{
	    	  Map mTemplateRange = new HashMap();
	        try {
	        	String strWOOid ="";
	            Map inputMap =  JPO.unpackArgs(args);
	            Map requestMap= (Map)inputMap.get("requestMap");
	            String strMEBOid= (String)requestMap.get("objectId");
	            DomainObject dom=DomainObject.newInstance(context,strMEBOid);
	            StringList slDisplay=new StringList();
	            StringList slActual=new StringList();
	             slDisplay.add(DomainConstants.EMPTY_STRING);
	             slActual.add(DomainConstants.EMPTY_STRING);
	            dom.setId(strMEBOid);
	            String strType = dom.getInfo(context, DomainConstants.SELECT_TYPE);
	            String sRangeToMatch="AMB Approval";
	            String strRel=RELATIONSHIP_WORKORDER_ABSTRACT_MBE;
	             if(strType.equals(TYPE_WMS_MEASUREMENT_BOOK_ENTRY)) {
	            	 strRel=RELATIONSHIP_WMS_WORK_ORDER_MBE;
	            	 sRangeToMatch="MB Approval";
	            }else if(strType.equals(TYPE_WMS_MATERIAL_BILL)) {
	            	 strRel=RELATIONSHIP_WMS_WO_MATERIAL_BILL;
	            	sRangeToMatch="Material Bill Approval";
	            }
	             strWOOid = dom.getInfo(context, "to["+strRel+"].from.id");
	            Map m=new HashMap();
	            m.put("objectId", strWOOid);
	            WMSWorkorder_mxJPO wmsWO=new  WMSWorkorder_mxJPO(context,args);
	            MapList mlTemplates =  wmsWO.getApprovalTemplate(context, JPO.packArgs(m));
	            Iterator<Map>  itr = mlTemplates.iterator();
	     
	            
	            String strAttribPurpose=DomainConstants.EMPTY_STRING;
	            while(itr.hasNext()) {
	               m = itr.next();
	               strAttribPurpose=(String) m.get("attribute["+ATTRIBUTE_WMS_APPROVAL_TEMPLATE_PURPOSE+"]");
	               if( strAttribPurpose.equals(sRangeToMatch)){
		                slDisplay.add((String)m.get(DomainConstants.SELECT_NAME));
		                slActual.add((String)m.get(DomainConstants.SELECT_ID));
		            } 
	            	
	            }
	             mTemplateRange.put("field_choices", slActual);
	             mTemplateRange.put("field_display_choices", slDisplay);
	            return mTemplateRange;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            throw ex;
	        }

		}
	
	    @com.matrixone.apps.framework.ui.CellRangeJPOCallable
	    public Map reloadMaterialMakersRanges(Context context, String[] args) throws Exception{

	        try {
	        	  String strMaterialOid = DomainObject.EMPTY_STRING;
	        	  StringList slRange          = new StringList();
	        	  StringList slActualRange          = new StringList();
	        	  Map newMakesrsRange = new HashMap();
	        	  Map m =  CacheUtil.getCacheMap(context, "matCache");
	        	  Map inputMap = (Map) JPO.unpackArgs(args);
	        	  if(inputMap.containsKey("rowValues")) {
	        		  Map rowValues = (Map) inputMap.get("rowValues");
		        	  String strStockId = (String) rowValues.get("objectId");
		        	  if(strStockId!=null && !strStockId.isEmpty()) {
		        		  DomainObject domStock = DomainObject.newInstance(context, strStockId);
		         		  strMaterialOid = domStock.getInfo(context, "from["+RELATIONSHIP_WMS_STOCK_MATERIAL+"].to.id");
		        		  m.put("matId", strMaterialOid);
		        	  }
	        	  }
		          Map paramMap = (Map)inputMap.get("paramMap");
	        	 
	        	  if(m!=null ) {
			        	 strMaterialOid  = (String)m.get("matId");
			        	if(strMaterialOid!=null && !strMaterialOid.isEmpty() && strMaterialOid.indexOf(".")!=-1) {
			             DomainObject domMaterial=DomainObject.newInstance(context,strMaterialOid);
			             
			                 String strExId = MqlUtil.mqlCommand(context, "print bus $1 $2 $3 select $4 dump", TYPE_WMS_MATERIAL_MAKER, "Others (Approval Taken)", "-","id");
			                 slActualRange.add(strExId);
			                 slRange.add("Others (Approval Taken)");
					             StringList slBusSelect           = new StringList();
								 slBusSelect.add(DomainConstants.SELECT_ID);
								 slBusSelect.add("attribute["+ATTRIBUTE_WMS_WORK_ORDER_TITLE+"]");
							     StringList slRelSelect           = new StringList();
								 slRelSelect.add(DomainRelationship.SELECT_ID);
					             MapList mlReturnList                	    = domMaterial.getRelatedObjects(context, // matrix context
			                             RELATIONSHIP_WMS_MAKER_OF, // relationship pattern
											TYPE_WMS_MATERIAL_MAKER, // type pattern
											slBusSelect, // object selects
											slRelSelect, // relationship selects
											true, // to direction
											false, // from direction
											(short) 1, // recursion level
											DomainConstants.EMPTY_STRING, // object where clause
											DomainConstants.EMPTY_STRING, // relationship where clause
											0);
					             Iterator<Map> itrMakers = mlReturnList.iterator();
					             while(itrMakers.hasNext()) {
					            	 Map mMaker= itrMakers.next();
					            	 slActualRange.add((String)mMaker.get(DomainConstants.SELECT_ID));
					            	 slRange.add((String) mMaker.get("attribute["+ATTRIBUTE_WMS_WORK_ORDER_TITLE+"]"));
					            	 
					             }
				         	  }
			        	  }
			             newMakesrsRange.put("RangeValues" ,  slActualRange);
			            newMakesrsRange.put("RangeDisplayValue" ,  slRange);
	            
	            return newMakesrsRange;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
	}
	    
	    
	    @com.matrixone.apps.framework.ui.CellRangeJPOCallable
	    public Map getMaterialMakersRanges(Context context, String[] args) throws Exception{

	        try {
	        	  StringList slRange          = new StringList();
	        	  StringList slActualRange          = new StringList();
	        	  Map newMakesrsRange = new HashMap();
	        	  Map inputMap = (Map) JPO.unpackArgs(args);
		          Map paramMap = (Map)inputMap.get("paramMap");
		          Map m =  CacheUtil.getCacheMap(context, "matCache");
	        	  if(m!=null ) {
			        	String strMaterialOid  = (String)m.get("matId");
			        	if(strMaterialOid!=null && !strMaterialOid.isEmpty() && strMaterialOid.indexOf(".")!=-1) {
	        	           DomainObject domMaterial=DomainObject.newInstance(context,strMaterialOid);
			                 String strExId = MqlUtil.mqlCommand(context, "print bus $1 $2 $3 select $4 dump", TYPE_WMS_MATERIAL_MAKER, "Others (Approval Taken)", "-","id");
			                      StringList slBusSelect           = new StringList();
								 slBusSelect.add(DomainConstants.SELECT_ID);
								 slBusSelect.add("attribute["+ATTRIBUTE_WMS_WORK_ORDER_TITLE+"]");
							     StringList slRelSelect           = new StringList();
								 slRelSelect.add(DomainRelationship.SELECT_ID);
					             MapList mlReturnList                	    = domMaterial.getRelatedObjects(context, // matrix context
			                             RELATIONSHIP_WMS_MAKER_OF, // relationship pattern
											TYPE_WMS_MATERIAL_MAKER, // type pattern
											slBusSelect, // object selects
											slRelSelect, // relationship selects
											true, // to direction
											false, // from direction
											(short) 1, // recursion level
											DomainConstants.EMPTY_STRING, // object where clause
											DomainConstants.EMPTY_STRING, // relationship where clause
											0);
					             Iterator<Map> itrMakers = mlReturnList.iterator();
					             while(itrMakers.hasNext()) {
					            	 Map mMaker= itrMakers.next();
					            	 slActualRange.add((String)mMaker.get(DomainConstants.SELECT_ID));
					            	 slRange.add((String) mMaker.get("attribute["+ATTRIBUTE_WMS_WORK_ORDER_TITLE+"]"));
					            	 
					             }
					             if(!slActualRange.contains(strExId)) {
					                  slActualRange.add(strExId);
				                      slRange.add("Others (Approval Taken)");
					             }
	        	             } 
			        	  }
	        	       
			             newMakesrsRange.put("field_choices" ,  slActualRange);
			            newMakesrsRange.put("field_display_choices" ,  slRange);
	            
	            return newMakesrsRange;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            throw ex;
	        }
	}

@com.matrixone.apps.framework.ui.CellRangeJPOCallable
public Map getStockEntriesRanges(Context context, String[] args) throws Exception{

	        try {
	        	Map m = JPO.unpackArgs(args);
	            Map requestMap= (Map)m.get("requestMap");
	            String strObjectId= (String)requestMap.get("objectId");
	        	m.put("objectId", strObjectId);
	        	m.put("FilterRange", "TRUE");
	             DomainObject domAMB=DomainObject.newInstance(context, strObjectId);
	            String strWorkOrderId = domAMB.getInfo(context, "to["+RELATIONSHIP_WMS_WORK_ORDER_ABSTRACT_MBE+"].from.id");
	            DomainObject domWO=DomainObject.newInstance(context,strWorkOrderId);
	            WMSMaterial_mxJPO wmsMaterial =new WMSMaterial_mxJPO(context,args);
	            MapList mlStockEntries =  wmsMaterial.getAllEscalatedMaterials(context,JPO.packArgs(m));
	            Iterator<Map> itrStockEntries = mlStockEntries.iterator();
	            StringList slRange          = new StringList();
	            slRange.add("All");
	            String strMateCatName=DomainConstants.EMPTY_STRING;
	            String strMatName=DomainConstants.EMPTY_STRING;
	            while(itrStockEntries.hasNext()) {
	            	Map mData =itrStockEntries.next();
	            	strMateCatName=(String)mData.get("from["+RELATIONSHIP_WMS_STOCK_MATERIAL+"].to.name");
	            	if(!slRange.contains(strMateCatName))
	            	    slRange.add(strMateCatName);
	             }
	            Map mRanges = new HashMap();
	            mRanges.put("field_choices", slRange);
	            mRanges.put("field_display_choices", slRange);
	            return mRanges;
	        } catch (Exception ex) {
	            ex.printStackTrace();
	            throw ex;
	        }

		}  
/**Gets called from create deliverables page :Work Order Contractor Documents 
 * 
 * @param context
 * @param args
 * @return
 */
	
@com.matrixone.apps.framework.ui.CellRangeJPOCallable 
 public Map  getContractorDeliverablesRanges(Context context,String[] args) {
  Map mRanges = new HashMap();
  try {
		String strDeliverables =  EnoviaResourceBundle.getProperty(context, "WMS.WO.ContractotDocument.DeliverablesList");
		String[] strFirstCommanSpilt = strDeliverables.split(",");
		StringList slRanges=new StringList(strFirstCommanSpilt.length);
		slRanges.add(DomainConstants.EMPTY_STRING);
		String[] strSecondPipeSpilt=new String[2];
		for(int i =0;i<strFirstCommanSpilt.length;i++) {
			strSecondPipeSpilt =strFirstCommanSpilt[i].split("\\|");
			slRanges.add(strSecondPipeSpilt[0]);
	 	}
		
		mRanges.put("field_choices", slRanges);
        mRanges.put("field_display_choices", slRanges);
  }catch(Exception e) {
	  e.printStackTrace();
  }
  
 return mRanges;
 	 
 }
	    
}
