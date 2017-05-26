package com.e2esp.nestleironcalculator.utils;

import android.content.Context;
import android.util.Xml;

import com.e2esp.nestleironcalculator.applications.NestleIronCalculatorApp;
import com.e2esp.nestleironcalculator.models.AgeSelection;
import com.e2esp.nestleironcalculator.models.ArrowCalculationRange;
import com.e2esp.nestleironcalculator.models.Category;
import com.e2esp.nestleironcalculator.models.DetailInstructions;
import com.e2esp.nestleironcalculator.models.Instruction;
import com.e2esp.nestleironcalculator.models.IronDetector;
import com.e2esp.nestleironcalculator.models.Popup;
import com.e2esp.nestleironcalculator.models.Product;
import com.e2esp.nestleironcalculator.models.Range;
import com.e2esp.nestleironcalculator.models.Result;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by farooq on 11-May-17.
 */

public class ParseXMLData {

    public String content = "IronDetector";
    private XmlPullParserFactory xmlFactoryObject;
    private XmlPullParser myparser;

    public void parse(Context context) throws XmlPullParserException, IOException{
        try {
            xmlFactoryObject = XmlPullParserFactory.newInstance();
            myparser = xmlFactoryObject.newPullParser();


            InputStream in_s = context.getAssets().open("data.xml");
            myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myparser.setInput(in_s, null);

            parseXML(myparser, context);

        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    private void parseXML(XmlPullParser parser, Context context) throws XmlPullParserException,IOException
    {
        IronDetector ironDetector = ((NestleIronCalculatorApp)context.getApplicationContext()).ironDetector;


        ArrayList<AgeSelection> ageSelection = null;
        ArrayList<Instruction> instructions = null;
        ArrayList<DetailInstructions> detInstructions = null;
        ArrayList<ArrowCalculationRange> arrowRanges = null;
        ArrayList<Category> categories = null;
        ArrayList<Product> products = null;
        ArrayList<Result> results= null;
        ArrayList<Range> ranges= null;
        ArrayList<Popup> popups= null;


        String currentUnderAgeText = "";
        boolean isAgeTagStart = false;
        boolean isInstructionsTagStart = false;
        boolean isDetInstructionsTagStart = false;
        boolean isArrowCalculationRangeStart = false;
        boolean isCategoryStart = false;
        boolean isProductStart = false;
        boolean isResultStart = false;
        boolean isRangeStart = false;
        boolean isPopupStart = false;
        boolean isNoMilkGivenStart = false;
        boolean isTooMuchMilkStart = false;
        boolean  isTooMuchSolidStart = false;

        int eventType = parser.getEventType();
        AgeSelection currentAgeSelection= null;
        Instruction currentInstruction = null;
        DetailInstructions currentDetInstruction = null;
        ArrowCalculationRange currentArrowRange = null;
        Product currentProduct = null;
        Category currentCategory = null;
        Result currentResult = null;
        Range currentRange = null;
        Popup currentPopup = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name = null;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    ageSelection = new ArrayList<>();
                    instructions = new ArrayList<>();
                    detInstructions = new ArrayList<>();
                    arrowRanges =  new ArrayList<>();
                    categories = new ArrayList<>();
                    results = new ArrayList<>();
                    ranges = new ArrayList<>();
                    popups = new ArrayList<>();

                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();

                    if (ironDetector != null) {

                        if (name.equals("RDA") && !isAgeTagStart) {
                            ironDetector.setRDA(Double.parseDouble(parser.nextText()));
                        } else if (name.equals("Milligramm")) {
                            ironDetector.setUnit(parser.nextText());
                        } else if (name.equals("CommaSign")) {
                            ironDetector.setCommaSign(parser.nextText());
                        } else if (name.equals("CommaDigits")) {
                            ironDetector.setCommaDigits(parser.nextText());
                        } else if (name.equals("RDAText")) {
                            ironDetector.setRDAText(parser.nextText());
                        } else if (name.equals("DetailedInstructions")) {
                            isInstructionsTagStart = true;
                            currentInstruction = new Instruction();
                        } else if (name.equals("CheckIntake") && isInstructionsTagStart) {
                            currentInstruction.setCheckIntake(parser.nextText());
                        } else if (name.equals("LegalCopyAsterisk") && isInstructionsTagStart) {
                            currentInstruction.setLegalCopyAsterisk(parser.nextText());
                        } else if (name.equals("LegalCopy") && isInstructionsTagStart) {
                            currentInstruction.setLegalCopy(parser.nextText());
                        } else if (name.equals("TryItNow") && isInstructionsTagStart) {
                            currentInstruction.setTryItNow(parser.nextText());
                        } else if (name.equals("DontShowThisNoticeAgain") && isInstructionsTagStart) {
                            currentInstruction.setDontShowThisNoticeAgain(parser.nextText());
                        } else if (name.equals("Instruction") && isInstructionsTagStart) {
                            currentDetInstruction = new DetailInstructions();
                            isDetInstructionsTagStart = true;
                        } else if (name.equals("Number") && isDetInstructionsTagStart) {
                            currentDetInstruction.setIns_Number(Integer.parseInt(parser.nextText()));
                        } else if (name.equals("IntructionText") && isDetInstructionsTagStart) {
                            currentDetInstruction.setIntructionText(parser.nextText());
                        } else if (name.equals("Age")) {
                            isAgeTagStart = true;
                            currentAgeSelection = new AgeSelection();
                        } else if (name.equals("RDA") && isAgeTagStart) {
                            currentAgeSelection.setRDA(Double.parseDouble(parser.nextText()));
                        } else if (name.equals("Text") && isAgeTagStart) {
                            currentAgeSelection.setText(parser.nextText());
                        } else if (name.equals("UnderAgeText"))
                            currentUnderAgeText = parser.nextText();
                        else if (name.equals("UnderAgeHintTitle")) {
                            if (ageSelection != null) {
                                for (int i = 0; i < ageSelection.size(); i++) {
                                    AgeSelection age = ageSelection.get(i);
                                    if (age.getText().equals(currentUnderAgeText)) {
                                        age.setTextHintTitle(parser.nextText());
                                    }
                                }
                            }
                        } else if (name.equals("UnderAgeHintText")) {
                            if (ageSelection != null) {
                                for (int i = 0; i < ageSelection.size(); i++) {
                                    AgeSelection age = ageSelection.get(i);
                                    if (age.getText().equals(currentUnderAgeText)) {
                                        age.setTextHint(parser.nextText());
                                    }
                                }
                            }

                        } else if (name.equals("CalcIronText")) {
                            ironDetector.setCalcIronText(parser.nextText());
                        }
                        else if (name.equals("CalculationRanges")) {
                            isArrowCalculationRangeStart = true;
                        }
                        else if (name.equals("Range")  && isArrowCalculationRangeStart) {
                            currentArrowRange = new ArrowCalculationRange();
                        }
                        else if (name.equals("Min") && isArrowCalculationRangeStart) {
                            currentArrowRange.setMin(Double.parseDouble(parser.nextText()));
                        }
                        else if (name.equals("Max") && isArrowCalculationRangeStart) {
                            currentArrowRange.setMax(Double.parseDouble(parser.nextText()));
                        }
                        else if (name.equals("Title") && isArrowCalculationRangeStart) {
                            currentArrowRange.setTitle(parser.nextText());
                        }
                        else if (name.equals("Text") && isArrowCalculationRangeStart) {
                            currentArrowRange.setText(parser.nextText());
                        }
                        else if (name.equals("Category")) {
                            isCategoryStart =true;
                            currentCategory = new Category();
                            products = new ArrayList<Product>();
                        }
                        else if (name.equals("CategoryId") && isCategoryStart) {
                            currentCategory.setCategoryId(parser.nextText());
                        }
                        else if (name.equals("CategoryName") && isCategoryStart) {
                            currentCategory.setCategoryName(parser.nextText());
                        }

                        else if (name.equals("Product")) {
                            isProductStart = true;
                            currentProduct = new Product();
                            getAttribute(currentProduct, parser);
                        }
                        else if (name.equals("minAge") && isProductStart) {
                            currentProduct.setMinAge( Integer.parseInt( parser.nextText())) ;
                        }
                        else if (name.equals("maxAge") && isProductStart) {
                            currentProduct.setMaxAge( Integer.parseInt( parser.nextText())) ;
                        }
                        else if (name.equals("Id") && isProductStart) {
                            currentProduct.setId(parser.nextText());
                        }
                        else if (name.equals("Name") && isProductStart) {
                            currentProduct.setName(parser.nextText());
                        }
                        else if (name.equals("Unit") && isProductStart) {
                            currentProduct.setUnit(parser.nextText());
                        }
                        else if (name.equals("PortionSize") && isProductStart) {
                            try {
                                double portionSize = Double.parseDouble(parser.nextText());
                                currentProduct.setPortionSize(portionSize);
                                currentProduct.setSelectedSize(portionSize);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        else if (name.equals("IronPer100mg") && isProductStart) {
                            try {
                                double ironPer100 = Double.parseDouble(parser.nextText());
                                currentProduct.setIronPer100mg(ironPer100);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        else if (name.equals("IronPerPortion") && isProductStart) {
                            try {
                                double ironPerPortion = Double.parseDouble(parser.nextText());
                                currentProduct.setIronPerPortion(ironPerPortion);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        }
                        else if (name.equals("Title") && isProductStart) {
                            currentProduct.setTitle(parser.nextText());
                        }
                        else if (name.equals("ResultPage") ) {
                            isResultStart = true;
                            currentResult = new Result();
                        }
                        else if (name.equals("Header") && isResultStart ) {
                            currentResult.setHeader(parser.nextText());
                        }
                        else if (name.equals("EstimatedIron") && isResultStart ) {
                            currentResult.setEstimatedIron(parser.nextText());
                        }
                        else if (name.equals("RDAHint") && isResultStart ) {
                            currentResult.setRDAHint(parser.nextText());
                        }else if (name.equals("RichFoodsText") && isResultStart ) {
                            currentResult.setRichFoodsText(parser.nextText());
                        }
                        else if (name.equals("Range") && isResultStart ) {
                            currentRange = new Range();
                            isRangeStart = true;
                        }
                        else if (name.equals("Min") && isRangeStart ) {
                            currentRange.setMin( Double.parseDouble(parser.nextText()) );
                        }
                        else if (name.equals("Max") && isRangeStart ) {
                            currentRange.setMax( Double.parseDouble(parser.nextText()) );
                        }
                        else if (name.equals("Title") && isRangeStart ) {
                            currentRange.setTitle( parser.nextText() );
                        }
                        else if (name.equals("Text") && isRangeStart ) {
                            currentRange.setText( parser.nextText() );
                        }

                        else if (name.equals("Popups") ) {
                            isPopupStart = true;
                            currentPopup = new Popup();
                        }
                        else if (name.equals("LetsProceed") && isPopupStart ) {
                            currentPopup.setLetsProceed( parser.nextText() );
                        }
                        else if (name.equals("DontShowNoticeAgain") && isPopupStart ) {
                            currentPopup.setDontShowThisNoticeAgain(parser.nextText() );
                        }
                        else if (name.equals("NoMilkGiven") && isPopupStart ) {
                            isNoMilkGivenStart = true;
                        }
                        else if (name.equals("Text") && isNoMilkGivenStart ) {
                            currentPopup.setNoMilkText(parser.nextText() );
                        }
                        else if (name.equals("TooMuchMilk") && isPopupStart ) {
                            isTooMuchMilkStart = true;
                        }
                        else if (name.equals("MaxMilk") && isTooMuchMilkStart ) {
                            currentPopup.setMaxMilkLimit( Integer.parseInt( parser.nextText()));
                        }
                        else if (name.equals("Text") && isTooMuchMilkStart ) {
                            currentPopup.setMaxMilkText( parser.nextText() );
                        }
                        else if (name.equals("TooMuchSolidFood") && isPopupStart ) {
                            isTooMuchSolidStart = true;
                        }
                        else if (name.equals("MaxFood") && isTooMuchSolidStart ) {
                            currentPopup.setMaxSolidLimit( Integer.parseInt( parser.nextText()));
                        }
                        else if (name.equals("Text") && isTooMuchSolidStart ) {
                            currentPopup.setMaxSolidText( parser.nextText() );
                        }

                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equalsIgnoreCase("Age") && isAgeTagStart) {
                        ageSelection.add(currentAgeSelection);
                        currentAgeSelection = null;
                        isAgeTagStart = false;
                    } else if (name.equalsIgnoreCase("Instructions") && isInstructionsTagStart) {
                        instructions.add(currentInstruction);
                        currentInstruction = null;
                        isInstructionsTagStart = false;

                    } else if (name.equalsIgnoreCase("Instruction") && isDetInstructionsTagStart) {
                        detInstructions.add(currentDetInstruction);
                        currentDetInstruction = null;
                        isDetInstructionsTagStart = false;

                    } else if (name.equalsIgnoreCase("InstructionsNumbered") && isInstructionsTagStart) {
                        currentInstruction.setDetailInstruction(detInstructions);
                    }
                    else if (name.equalsIgnoreCase("Range") && isArrowCalculationRangeStart) {
                        arrowRanges.add(currentArrowRange);
                        currentArrowRange = null;
                    }
                    else if (name.equalsIgnoreCase("CalculationRanges") && isArrowCalculationRangeStart) {
                        isArrowCalculationRangeStart = false;
                        ironDetector.setArrowCalcRanges(arrowRanges);
                    }
                    else if (name.equalsIgnoreCase("Category") && isCategoryStart) {
                        isCategoryStart = false;
                        categories.add(currentCategory);
                    }
                    else if (name.equalsIgnoreCase("Product") && isProductStart) {
                        isProductStart = false;
                        products.add(currentProduct);
                        currentProduct = null;

                    }
                    else if (name.equalsIgnoreCase("Products") && isCategoryStart) {
                        currentCategory.setProducts(products);
                        products = null;
                    }
                    else if (name.equalsIgnoreCase("Range") && isRangeStart) {
                        isRangeStart = false;
                        ranges.add(currentRange);
                        currentRange = null;
                    }

                    else if (name.equalsIgnoreCase("ResultPage") && isResultStart) {
                        isResultStart = false;
                        currentResult.setRanges(ranges);
                        results.add(currentResult);
                    }
                    else if (name.equalsIgnoreCase("NoMilkGiven") && isNoMilkGivenStart) {
                        isNoMilkGivenStart = false;
                    }
                    else if (name.equalsIgnoreCase("TooMuchMilk") && isTooMuchMilkStart) {
                        isTooMuchMilkStart = false;
                    }
                    else if (name.equalsIgnoreCase("TooMuchSolidFood") && isTooMuchSolidStart) {
                        isTooMuchSolidStart = false;
                    }
                    else if (name.equalsIgnoreCase("Popups") && isPopupStart) {
                        isPopupStart = false;
                        popups.add(currentPopup);
                    }



            }
            eventType = parser.next();
        }
        ironDetector.setAge(ageSelection);
        ironDetector.setInstructions(instructions);
        ironDetector.setCategories(categories);
        ironDetector.setResults(results);
        ironDetector.setPopups(popups);

        //print(ironDetector);
    }

    private void getAttribute(Product prod, XmlPullParser parser)
    {

        //Map<String,String> attrs=null;
        int count =parser.getAttributeCount();
        if(count != -1) {
          //  attrs = new HashMap<>(acount);
            for(int x=0; x<count ; x++) {
                String atrName = parser.getAttributeName(x);
                if (Consts.iron_rich_attribute.equals(atrName)) {
                    prod.setIronRichFood(Boolean.parseBoolean(parser.getAttributeValue(x)));
                    // attrs.put(parser.getAttributeName(x), parser.getAttributeValue(x));
                }

            }
        }

    }

    private void print(IronDetector ironDetector) {
        content = "IronDetector";
        //content = content + "RDA :" + ironDetector.getRDA() + "\n";
        //content = content + "Unit :" + ironDetector.getUnit() + "\n";
        //content = content + "CommaSign :" + ironDetector.getCommaSign() + "\n";
        //content = content + "CommaDigits :" + ironDetector.getCommaDigits() + "\n";
        //content = content + "CalcIronText :" + ironDetector.getCalcIronText() + "\n";


        content = content + " Popups \n";

        ArrayList<Popup> popups = ironDetector.getPopups();

        for(int i=0; i< popups.size(); i++)
        {
            Popup currPopup = popups.get(i);
            if(currPopup != null) {
                content = content + "Lets Proceed :" + currPopup.getLetsProceed() + "\n";
                content = content + "Dont show:" + currPopup.getDontShowThisNoticeAgain() + "\n";
                content = content + "No Milk Text:" + currPopup.getNoMilkText() + "\n";
                content = content + "Max Milk Limit:" + currPopup.getMaxMilkLimit() + "\n";
                content = content + "Max Milk Text:" + currPopup.getMaxMilkText() + "\n";
                content = content + "Max Solid Limit:" + currPopup.getMaxSolidLimit() + "\n";
                content = content + "Max Solid Text:" + currPopup.getMaxSolidText() + "\n";

            }
        }

        /*
        content = content + " Result Page \n";

        ArrayList<Result> results = ironDetector.getResults();

        for(int i=0; i< results.size(); i++)
        {
            Result currResult = results.get(i);
            if(currResult != null) {
                content = content + "Header :" + currResult.getHeader() + "\n";
                content = content + "Estimated Iron:" + currResult.getEstimatedIron() + "\n";
                content = content + "RDAHint:" + currResult.getRDAHint() + "\n";
                content = content + "RichFoodsText:" + currResult.getRichFoodsText() + "\n";

                content = content + " Ranges \n";

                ArrayList<Range> ranges= currResult.getRanges();

                for(int j=0; j< ranges.size(); j++) {
                    Range currRange= ranges.get(j);
                    if (currRange != null) {
                        content = content + "Min :" + currRange.getMin() + "\n";
                        content = content + "Max :" + currRange.getMax() + "\n";
                        content = content + "Title :" + currRange.getTitle() + "\n";
                        content = content + "Text :" + currRange.getText() + "\n";
                    }
                }
            }
        }

        content = content + " Categories \n";

        ArrayList<Category> categories = ironDetector.getCategories();

        for(int i=0; i< categories.size(); i++)
        {
            Category currCat = categories.get(i);
            if(currCat != null) {
                content = content + "CategoryId :" + currCat.getCategoryId() + "\n";
                content = content + "Category Name :" + currCat.getCategoryName() + "\n";

                content = content + " Product \n";

                ArrayList<Product> products = currCat.getProducts();
                for(int j=0; j< products.size(); j++) {
                    Product prod = products.get(j);
                    if (prod != null) {
                        content = content + "Id :" + prod.getId() + "\n";
                        content = content + "Name :" + prod.getName() + "\n";
                        content = content + "Unit :" + prod.getUnit() + "\n";
                        content = content + "PortionSize :" + prod.getPortionSize() + "\n";
                        content = content + "IronPer100mg :" + prod.getIronPer100mg() + "\n";
                        content = content + "IronPerPortion :" + prod.getIronPerPortion() + "\n";
                        content = content + "Title :" + prod.getTitle() + "\n";


                    }
                }
            }
        }


       content = content + " Arrow Ranges \n";

        ArrayList<ArrowCalculationRange> ranges = ironDetector.getArrowCalculationRanges();

        for(int i=0; i< ranges.size(); i++)
        {
            ArrowCalculationRange currRanges = ranges.get(i);
            if(currRanges != null) {
                content = content + "Min :" + currRanges.getMin().toString() + "\n";
                content = content + "Max :" + currRanges.getMax().toString() + "\n";
                content = content + "Postition:" + currRanges.getPosition() + "\n";
                content = content + "Title :" + currRanges.getTitle() + "\n";
                content = content + "ResultText :" + currRanges.getResultText() + "\n";
            }
        }

       ArrayList<Instruction> instructions = ironDetector.getInstruction();

        for(int i=0; i< instructions.size(); i++)
        {
            Instruction currInstructions = instructions.get(i);
            if(currInstructions != null) {
                content = content + "CheckIntake:" + currInstructions.getCheckIntake() + "\n";
                content = content + "LegalCopy :" + currInstructions.getLegalCopy() + "\n";
                content = content + "LegalCopyAsterisk :" + currInstructions.getLegalCopyAsterisk() + "\n";
                content = content + "TryItNow :" + currInstructions.getTryItNow() + "\n";
                content = content + "DontShowThisNoticeAgain :" + currInstructions.getDontShowThisNoticeAgain() + "\n";

                content = content + " Instructions Detail \n";

                ArrayList<DetailInstructions> detInstructions = currInstructions.getDetailInstruction();
                for(int j=0; j< detInstructions.size(); j++)
                {
                    DetailInstructions det = detInstructions.get(j);
                    if(det != null) {
                        content = content + "Ins_Number:" + det.getIns_Number() + "\n";
                        content = content + "Ins_Text:" + det.getIntructionText() + "\n";
                    }
                }

            }
        }

        content = content + " AGE SELECTION \n";

        ArrayList<AgeSelection> ageSelections = ironDetector.getAge();
        Iterator<AgeSelection> it = ageSelections.iterator();
        for(int i=0; i< ageSelections.size(); i++)
        {
            AgeSelection currAgeSelection = ageSelections.get(i);
            if(currAgeSelection != null) {
                content = content + "RDA :" + currAgeSelection.getRDA() + "\n";
                content = content + "Text :" + currAgeSelection.getText() + "\n";
                content = content + "TextHintTitle :" + currAgeSelection.getTextHintTitle() + "\n";
                content = content + "TextHint :" + currAgeSelection.getTextHint() + "\n";
            }
        }*/


    }

}
