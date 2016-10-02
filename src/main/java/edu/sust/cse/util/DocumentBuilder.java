package edu.sust.cse.util;

import org.apache.commons.codec.language.bm.Languages;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by Biswajit Debnath on 30-Sep-16.
 */
public class DocumentBuilder {
    private String blackColor = "000000";

    public void createDocx(String fileName) throws IOException {

        XWPFDocument document = new XWPFDocument();

        FileOutputStream out = new FileOutputStream(new File(fileName));

        //create table
        XWPFTable table = document.createTable();
        table.getCTTbl().getTblPr().unsetTblBorders();

        //create first row
        XWPFTableRow tableRowOne = table.getRow(0);
        for(int i=0;i<3;i++){
            tableRowOne.addNewTableCell();
        }
        setRun(tableRowOne.getCell(0).addParagraph().createRun(), "Siyam Rupali",22, blackColor,"আট জেলায় সংঘর্ষে আহত ১১০", true, false);


        XWPFTableRow tableRowTwo = table.createRow();



        setRun(tableRowTwo.getCell(0).addParagraph().createRun(), "Siyam Rupali", 8, blackColor, "ব্রাহ্মণবাড়িয়া:\n" +
                " আশুগঞ্জ উপজেলায় নির্বাচন-পরবর্তী সহিংসতায় আহত হয়েছেন ৫০ জনের বেশি নারী-পুরুষ। উপজেলার দুর্গাপুর গ্রামে বৃহস্পতিবার ও শুক্রবার সকালে এ সংঘর্ষের ঘটনা ঘটে।\n" +
                "পুলিশ ও প্রত্যক্ষদর্শীরা জানান, ২২ মার্চ প্রথম ধাপের ইউপি নির্বাচনকে কেন্দ্র করে বৃহস্পতিবার দুর্গাপুর ইউনিয়নে আওয়ামী লীগের বিদ্রোহী প্রার্থী মোয়াজ্জেম হোসেন মাজু মিয়ার সমর্থকদের ওপর হামলা করেন আওয়ামী লীগের প্রার্থী জিয়াউল করিম খানের সমর্থকেরা। ওই সংঘর্ষের জেরে গতকালও দুই পক্ষের মধ্যে পাল্টাপাল্টি হামলার ঘটনা ঘটে। দুই দিনের সহিংসতায় এই ইউনিয়নে ৫০ জনের বেশি লোক আহত হয়েছেন।\n" +
                "আশুগঞ্জ থানার ভারপ্রাপ্ত কর্মকর্তা (ওসি) মুহাম্মদ সেলিম উদ্দিন জানান, বর্তমানে এলাকার পরিস্থিতি স্বাভাবিক আছে। ঘটনাস্থলে অতিরিক্ত পুলিশ মোতায়েন রয়েছে।\n" +
                "এদিকে নবীনগর উপজেলার বীরগাঁও ইউনিয়নে গতকাল দুপুরে দুই পক্ষের সংঘর্ষে অন্তত ১৫ জন আহত হয়েছেন। স্থানীয় ব্যক্তিরা বলেন, বৃহস্পতিবার বীরগাঁও ইউপি চেয়ারম্যান পদে আওয়ামী লীগের বিদ্রোহী প্রার্থী ও বর্তমান চেয়ারম্যান কবির আহমেদ জয়ী হন। শুক্রবার বেলা সাড়ে ১১টার দিকে ইউনিয়নের বাইশমোজা এলাকায় কবির আহমেদ ও তাঁর সমর্থকদের ওপর হেরে যাওয়া স্বতন্ত্র প্রার্থী কবির হোসেন ও তাঁর লোকজন হামলা চালান। এর জেরে উভয় পক্ষের সমর্থকেরা সংঘর্ষে জড়িয়ে পড়েন।\n" +
                "অপর ঘটনায় নবীনগর উপজেলা সদরে বিজিবি সদস্যদের সঙ্গে বীরগাঁও ইউনিয়নের আওয়ামী লীগের পরাজিত চেয়ারম্যান প্রার্থী জহির রায়হানের কর্মী-সমর্থকের ধাওয়া-পাল্টাধাওয়ার ঘটনা ঘটেছে গতকাল দুপুরে।\n" +
                "স্থানীয় ব্যক্তিরা বলেন, বেলা দেড়টার দিকে বীরগাঁও ইউপি নির্বাচনের ভোট পুনরায় গণনার দাবিতে বিক্ষোভ মিছিল নিয়ে নবীনগর উপজেলায় ইউএনও কার্যালয় ঘেরাও করেন জহির রায়হান ও তাঁর কর্মী-সমর্থকেরা। এ সময় তাঁদের সঙ্গে", false, false);


        setRun(tableRowTwo.addNewTableCell().addParagraph().createRun(), "Siyam Rupali", 8, blackColor, "ব্রাহ্মণবাড়িয়া:\n" +
                " আশুগঞ্জ উপজেলায় নির্বাচন-পরবর্তী সহিংসতায় আহত হয়েছেন ৫০ জনের বেশি নারী-পুরুষ। উপজেলার দুর্গাপুর গ্রামে বৃহস্পতিবার ও শুক্রবার সকালে এ সংঘর্ষের ঘটনা ঘটে।\n" +
                "পুলিশ ও প্রত্যক্ষদর্শীরা জানান, ২২ মার্চ প্রথম ধাপের ইউপি নির্বাচনকে কেন্দ্র করে বৃহস্পতিবার দুর্গাপুর ইউনিয়নে আওয়ামী লীগের বিদ্রোহী প্রার্থী মোয়াজ্জেম হোসেন মাজু মিয়ার সমর্থকদের ওপর হামলা করেন আওয়ামী লীগের প্রার্থী জিয়াউল করিম খানের সমর্থকেরা। ওই সংঘর্ষের জেরে গতকালও দুই পক্ষের মধ্যে পাল্টাপাল্টি হামলার ঘটনা ঘটে। দুই দিনের সহিংসতায় এই ইউনিয়নে ৫০ জনের বেশি লোক আহত হয়েছেন।\n" +
                "আশুগঞ্জ থানার ভারপ্রাপ্ত কর্মকর্তা (ওসি) মুহাম্মদ সেলিম উদ্দিন জানান, বর্তমানে এলাকার পরিস্থিতি স্বাভাবিক আছে। ঘটনাস্থলে অতিরিক্ত পুলিশ মোতায়েন রয়েছে।\n" +
                "এদিকে নবীনগর উপজেলার বীরগাঁও ইউনিয়নে গতকাল দুপুরে দুই পক্ষের সংঘর্ষে অন্তত ১৫ জন আহত হয়েছেন। স্থানীয় ব্যক্তিরা বলেন, বৃহস্পতিবার বীরগাঁও ইউপি চেয়ারম্যান পদে আওয়ামী লীগের বিদ্রোহী প্রার্থী ও বর্তমান চেয়ারম্যান কবির আহমেদ জয়ী হন। শুক্রবার বেলা সাড়ে ১১টার দিকে ইউনিয়নের বাইশমোজা এলাকায় কবির আহমেদ ও তাঁর সমর্থকদের ওপর হেরে যাওয়া স্বতন্ত্র প্রার্থী কবির হোসেন ও তাঁর লোকজন হামলা চালান। এর জেরে উভয় পক্ষের সমর্থকেরা সংঘর্ষে জড়িয়ে পড়েন।\n" +
                "অপর ঘটনায় নবীনগর উপজেলা সদরে বিজিবি সদস্যদের সঙ্গে বীরগাঁও ইউনিয়নের আওয়ামী লীগের পরাজিত চেয়ারম্যান প্রার্থী জহির রায়হানের কর্মী-সমর্থকের ধাওয়া-পাল্টাধাওয়ার ঘটনা ঘটেছে গতকাল দুপুরে।\n" +
                "স্থানীয় ব্যক্তিরা বলেন, বেলা দেড়টার দিকে বীরগাঁও ইউপি নির্বাচনের ভোট পুনরায় গণনার দাবিতে বিক্ষোভ মিছিল নিয়ে নবীনগর উপজেলায় ইউএনও কার্যালয় ঘেরাও করেন জহির রায়হান ও তাঁর কর্মী-সমর্থকেরা। এ সময় তাঁদের সঙ্গে", false, false);

        setRun(tableRowTwo.addNewTableCell().addParagraph().createRun(), "Siyam Rupali", 8, blackColor, "পঞ্চগড়: বোদা উপজেলার ঝলই শালশিরি ইউনিয়নে ১ নম্বর ওয়ার্ডে ইউপি সদস্যপদে পরাজিত প্রার্থী শঙ্কর পদ দের সমর্থকেরা বিজয়ী প্রার্থী দাহিরউদ্দিনের সমর্থকদের ওপর হামলা চালায় গতকাল সকালে। এ ঘটনায় অন্তত পাঁচজন আহত হন।\n" +
                "নেত্রকোনা: আটপাড়ার দুওজ ইউনিয়নের ৩ নম্বর ওয়ার্ডের পরাজিত ইউপি সদস্য প্রার্থী হারু মিয়ার সমর্থকেরা বৃহস্পতিবার রাতে ইউনিয়নের গোপালপুর গ্রামে বাসিন্দা ও মহেশ্বরখিলা সরকারি প্রাথমিক বিদ্যালয়ের অবসরপ্রাপ্ত প্রধান শিক্ষক করুণা কেতন মিত্রকে (৮০) মারধর করে।\n" +
                "অন্য ঘটনায়, একই উপজেলার তেলিগাতি ইউনিয়নের ৭ নম্বর ওয়ার্ডের বিজয়ী ইউপি সদস্য প্রার্থী ফিরোজ মিয়ার সমর্থকেরা পরাজিত ইউপি সদস্য প্রার্থী বিকাশ শর্মার দুই ভাতিজাকে গতকাল পিটিয়ে আহত করেছেন।\n" +
                "শেরপুর: শেরপুরের নকলায় নির্বাচনোত্তর সহিংসতায় পরাজিত চেয়ারম্যান প্রার্থী নূরে আলম তালুকদারের সমর্থকদের আটটি বাড়ি ও দোকানপাট ভাঙচুর করা হয়েছে বৃহস্পতিবার রাতে নির্বাচনী ফলাফল ঘোষণার পর। নির্বাচনে বিজয়ী আওয়ামী লীগের প্রার্থী রেজাউলে হকের সমর্থকদের এই হামলার জন্য দায়ী করেছেন নূরে আলম তালুকদার।\n" +
                "কেরানীগঞ্জ: কেরানীগঞ্জ উপজেলার হযরতপুর ইউনিয়নে মধুরচর সরকারি প্রাথমিক বিদ্যালয়ের ভোটকেন্দ্রে সন্ত্রাসীদের গুলিতে নিহত শুভ কাজীর দাফন সম্পন্ন হয়েছে। শুভ কাজী খুনের ঘটনায় তাঁর চাচা সালাল কাজী বাদী হয়ে বৃহস্পতিবার রাতে কেরানীগঞ্জ মডেল থানায় মামলা দায়ের করেছেন। এতে অজ্ঞাতনামা ২০ থেকে ২২ জন ব্যক্তিকে আসামি করা হয়েছে। এ হত্যাকাণ্ডের ঘটনায় এখন পর্যন্ত কাউকে গ্রেপ্তার করা যায়নি বলে জানিয়েছে পুলিশ।\n" +
                "মানিকগঞ্জ: গতকাল দুপুরে দৌলতপুর উপজেলার বাঁচামারা ইউনিয়নে নির্বাচন-পরবর্তী সহিংসতায় আওয়ামী লীগের পরাজিত প্রার্থী আবদুর রশিদের সমর্থকদের ওপর হামলা চালান নির্বাচনে জয়ী ও আওয়ামী লীগের বিদ্রোহী প্রার্থী আবদুল লতিফের সমর্থকেরা।\n", false, false);

        document.write(out);
        out.close();
    }


    private void setRun(XWPFRun run, String fontFamily, int fontSize, String colorRGB, String text, boolean bold, boolean addBreak) {
        run.setFontFamily(fontFamily);
        run.setFontSize(fontSize);
        run.setColor(colorRGB);
        run.setText(text);
        run.setBold(bold);

        if (addBreak) run.addBreak();
    }


}
