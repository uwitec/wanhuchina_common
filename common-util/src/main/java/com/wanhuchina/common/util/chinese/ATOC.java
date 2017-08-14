package com.wanhuchina.common.util.chinese;

public class ATOC {

    public static String[] chineseDigits = new String[] { "\u96F6", "\u58F9", "\u8D30", "\u53C1", "\u8086", "\u4F0D",
            "\u9646", "\u67D2", "\u634C", "\u7396"};

    /**
     *
     * @param amount
     * @return
     */
    public static String amountToChinese(double amount) {
        if(amount > 99999999999999.99 || amount < -99999999999999.99)
            throw new IllegalArgumentException("Invalid value must be between -99999999999999.99 and 99999999999999.99)");

        boolean negative = false;
        if(amount < 0) {
            negative = true;
            amount = amount * (-1);
        }

        long temp = Math.round(amount * 100);
        int numFen = (int)(temp % 10);
        temp = temp / 10;
        int numJiao = (int)(temp % 10);
        temp = temp / 10;


        int[] parts = new int[20];
        int numParts = 0;
        for(int i=0; ; i++) {
            if(temp ==0)
                break;
            int part = (int)(temp % 10000);
            parts[i] = part;
            numParts ++;
            temp = temp / 10000;
        }

        boolean beforeWanIsZero = true;

        String chineseStr = "";
        for(int i=0; i<numParts; i++) {

            String partChinese = partTranslate(parts[i]);
            if(i % 2 == 0) {
                if("".equals(partChinese))
                    beforeWanIsZero = true;
                else
                    beforeWanIsZero = false;
            }

            if(i != 0) {
                if(i % 2 == 0)
                    chineseStr = "\u4EBF" + chineseStr;
                else {
                    if("".equals(partChinese) && !beforeWanIsZero)
                        chineseStr = "\u96F6" + chineseStr;
                    else {
                        if(parts[i-1] < 1000 && parts[i-1] > 0)
                            chineseStr = "\u96F6" + chineseStr;
                        chineseStr = "\u4E07" + chineseStr;
                    }
                }
            }
            chineseStr = partChinese + chineseStr;
        }

        if("".equals(chineseStr))
            chineseStr = chineseDigits[0];
        else if(negative)
            chineseStr = "\u8D1F" + chineseStr;

        chineseStr = chineseStr + "\u5143";

        if(numFen == 0 && numJiao == 0) {
            chineseStr = chineseStr + "\u6574";
        }
        else if(numFen == 0) {
            chineseStr = chineseStr + chineseDigits[numJiao] + "\u89D2";
        }
        else {
            if(numJiao == 0)
                chineseStr = chineseStr + "\u96F6" + chineseDigits[numFen] + "\u5206";
            else
                chineseStr = chineseStr + chineseDigits[numJiao] + "\u89D2" + chineseDigits[numFen] + "\u5206";
        }

        return chineseStr;

    }


    /**
     * @param amountPart
     * @return
     */
    private static String partTranslate(int amountPart) {
        if(amountPart < 0 || amountPart > 10000) {
            throw new IllegalArgumentException("Invalid value, must be between 0 and 10000");
        }


        String[] units = new String[] {"", "\u62FE", "\u4F70", "\u4EDF"};

        int temp = amountPart;

        String amountStr = new Integer(amountPart).toString();
        int amountStrLength = amountStr.length();
        boolean lastIsZero = true;
        String chineseStr = "";

        for(int i=0; i<amountStrLength; i++) {
            if(temp == 0)
                break;
            int digit = temp % 10;
            if(digit == 0) {
                if(!lastIsZero)
                    chineseStr = "\u96F6" + chineseStr;
                lastIsZero = true;
            }
            else {
                chineseStr = chineseDigits[digit] + units[i] + chineseStr;
                lastIsZero = false;
            }
            temp = temp / 10;
        }
        return chineseStr;
    }
}

