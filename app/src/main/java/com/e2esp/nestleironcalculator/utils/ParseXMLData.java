package com.e2esp.nestleironcalculator.utils;

import android.util.Xml;

import com.e2esp.nestleironcalculator.applications.NestleIronCalculatorApp;
import com.e2esp.nestleironcalculator.models.AgeSelection;
import com.e2esp.nestleironcalculator.models.ArrowCalculationRange;
import com.e2esp.nestleironcalculator.models.Category;
import com.e2esp.nestleironcalculator.models.DetailInstructions;
import com.e2esp.nestleironcalculator.models.Instruction;
import com.e2esp.nestleironcalculator.models.IronDetector;
import com.e2esp.nestleironcalculator.models.Product;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by farooq on 11-May-17.
 */

public class ParseXMLData {

    public String content = "IronDetector";
    private XmlPullParserFactory xmlFactoryObject;
    private XmlPullParser myparser;

    public void parse() throws XmlPullParserException, IOException{



        try {

            xmlFactoryObject = XmlPullParserFactory.newInstance();
            myparser = xmlFactoryObject.newPullParser();


            InputStream in_s = NestleIronCalculatorApp.getAppContext().getAssets().open("data.xml");
            myparser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            myparser.setInput(in_s, null);

            parseXML(myparser);

        } catch (XmlPullParserException e) {

            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
    private void parseXML(XmlPullParser parser) throws XmlPullParserException,IOException
    {
        IronDetector ironDetector = new IronDetector();
        ArrayList<AgeSelection> ageSelection = null;
        ArrayList<Instruction> instructions = null;
        ArrayList<DetailInstructions> detInstructions = null;
        ArrayList<ArrowCalculationRange> arrowRanges = null;
        ArrayList<Category> categories = null;
        ArrayList<Product> products = null;


        boolean isAgeTagStart = false;
        String currentUnderAgeText = "";
        boolean isInstructionsTagStart = false;
        boolean isDetInstructionsTagStart = false;
        boolean isArrowCalculationRangeStart = false;
        boolean isCategoryStart = false;
        boolean isProductStart = false;

        int eventType = parser.getEventType();
        AgeSelection currentAgeSelection= null;
        Instruction currentInstruction = null;
        DetailInstructions currentDetInstruction = null;
        ArrowCalculationRange currentArrowRange = null;
        Product currentProduct = null;
        Category currentCategory = null;

        while (eventType != XmlPullParser.END_DOCUMENT) {
            String name = null;
            switch (eventType) {
                case XmlPullParser.START_DOCUMENT:
                    ageSelection = new ArrayList<AgeSelection>();
                    instructions = new ArrayList<Instruction>();
                    detInstructions = new ArrayList<DetailInstructions>();
                    arrowRanges =  new ArrayList<ArrowCalculationRange>();
                    categories = new ArrayList<Category>();
                    products = new ArrayList<Product>();

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
                        else if (name.equals("ArrowCalculation")) {
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
                        else if (name.equals("Position") && isArrowCalculationRangeStart) {
                            currentArrowRange.setPosition(Double.parseDouble(parser.nextText()));
                        }
                        else if (name.equals("Title") && isArrowCalculationRangeStart) {
                            currentArrowRange.setTitle(parser.nextText());
                        }
                        else if (name.equals("ResultText") && isArrowCalculationRangeStart) {
                            currentArrowRange.setResultText(parser.nextText());
                        }
                        else if (name.equals("Category")) {
                            isCategoryStart =true;
                            currentCategory = new Category();
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
                            currentProduct.setPortionSize(parser.nextText());
                        }
                        else if (name.equals("IronPer100mg") && isProductStart) {
                            currentProduct.setIronPer100mg(Double.parseDouble(parser.nextText()));
                        }
                        else if (name.equals("IronPerPortion") && isProductStart) {
                            currentProduct.setIronPerPortion(Double.parseDouble(parser.nextText()));
                        }
                        else if (name.equals("Title") && isProductStart) {
                            currentProduct.setTitle(parser.nextText());
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
                    else if (name.equalsIgnoreCase("ArrowCalculation") && isArrowCalculationRangeStart) {
                        isArrowCalculationRangeStart = false;
                        ironDetector.setArrowCalculationRanges(arrowRanges);
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
                       // products = new ArrayList<Product>();
                    }


            }
            eventType = parser.next();
        }
        ironDetector.setAge(ageSelection);
        ironDetector.setInstruction(instructions);
        ironDetector.setCategories(categories);

        print(ironDetector);
    }

    private void print(IronDetector ironDetector) {
        content = "IronDetector";
        //content = content + "RDA :" + ironDetector.getRDA() + "\n";
        //content = content + "Unit :" + ironDetector.getUnit() + "\n";
        //content = content + "CommaSign :" + ironDetector.getCommaSign() + "\n";
        //content = content + "CommaDigits :" + ironDetector.getCommaDigits() + "\n";
        //content = content + "CalcIronText :" + ironDetector.getCalcIronText() + "\n";

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


       /* content = content + " Arrow Ranges \n";

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
