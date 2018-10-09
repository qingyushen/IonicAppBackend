package com.bs.doc.generation;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;

import com.bs.mapper.TodoTaskMapper;
import com.bs.model.todoTask;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class CreateDocument {
    @Autowired
    private TodoTaskMapper taskMapper;

    public void generate(String grpName) throws Exception {
        List<todoTask> tasks = taskMapper.getAllByGroupNameAndVisible(grpName, 1);
        System.out.println("Finished getting tasks;");

        // Initialize the document
        try {
            XWPFDocument document = new XWPFDocument();
            FileOutputStream out = new FileOutputStream(new File("iPlat4C-Weekly.docx"));

            // Add the logo
            XWPFParagraph image = document.createParagraph();
            image.setAlignment(ParagraphAlignment.LEFT);
            XWPFRun imageRun = image.createRun();
            imageRun.setTextPosition(20);
            File picture = new File("./assets/bsLogo.png");
            FileInputStream in = new FileInputStream(picture);
            imageRun.addPicture(in, Document.PICTURE_TYPE_PNG, "bsLogo.png", 100, 100);

            // title setting
            XWPFParagraph title = document.createParagraph();
            title.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun titleRun = title.createRun();
            titleRun.setText("MES平台技术（iPlat4C,java,移动）项目周报");
            titleRun.setBold(true);
            titleRun.setFontFamily("Courier"); // todo make times new roman
            titleRun.setFontSize(13);

            // subtitle setting - date period for the weekly report
            XWPFParagraph subTitle = document.createParagraph();
            subTitle.setAlignment(ParagraphAlignment.CENTER);

            XWPFRun subTitleRun = subTitle.createRun();
            subTitleRun.setText("时间：2018/08/06~2018/08/12");
            subTitleRun.setFontFamily("Courier");
            subTitleRun.setFontSize(12);
            subTitleRun.setTextPosition(20);

            XWPFParagraph paragraph = document.createParagraph();
            XWPFRun run = paragraph.createRun();

            // Loop through all the tasks, keep track of changes in assignedToID, Completed boolean and
            // dueDate
            Integer currentUser = 0;
            Integer completed = 0;
            int count = 2;
            String dueDate;
            if (tasks.size() > 0) {
                todoTask curTask = tasks.get(0);
                currentUser = curTask.getAssignedToID();
                completed = curTask.getDone();
                run.setText(curTask.getAssignedToName());
                run.addBreak();
                run.setText("(1)   " + curTask.getDueDate() + ": " + curTask.getBodyText());
                run.addBreak();
            }
            for (int i = 1; i < tasks.size(); i++) {
                todoTask curTask = tasks.get(i);
                // Case 1: no param changes, just print
                if (currentUser.equals(curTask.getAssignedToID()) && completed.equals(curTask.getDone())) {
                    run.setText("(" + (count++) + ")   " + curTask.getDueDate() + ": " + curTask.getBodyText());
                    run.addBreak();
                }
                // case 2: completed is now set to 1 so the change
                else if (currentUser.equals(curTask.getAssignedToID())) {
                    run.setText("Completed:");
                    run.addBreak();
                    count = 1;
                    run.setText("(" + (count++) + ")   " + curTask.getDueDate() + ": " + curTask.getBodyText());
                    run.addBreak();
                    completed = 1;
                }
                // Case 3: next person detected
                else {
                    completed = curTask.getDone();
                    run.setText(curTask.getAssignedToName());
                    run.addBreak();
                    if (completed == 0) {
                        run.setText("本周实绩：");
                    } else {
                        run.setText("下周计划：");
                    }
                    run.setText("(1)   " + curTask.getDueDate() + ": " + curTask.getBodyText());
                    run.addBreak();
                    count = 2;
                }
            }
            document.write(out);
            out.close();
            document.close();
            System.out.println("Finished Generation");
        } catch (Exception e) {
            System.out.println("unknown error happens when generating the file");
        }
    }


//  public static void main(String[] args)throws Exception  {
//
//
//
//
//    //Blank Document
//    XWPFDocument document = new XWPFDocument();
//
//    //Write the Document in file system
//    FileOutputStream out = new FileOutputStream( new File("createdocument.docx"));
//    XWPFParagraph paragraph = document.createParagraph();
//    XWPFRun run = paragraph.createRun();
//
//
//    File picture = new File("./assets/bsLogo.png");
//    System.out.println("directory is: " + (picture.exists()));
//    FileInputStream in = new FileInputStream(picture);
//    run.addPicture(in, Document.PICTURE_TYPE_PNG, "bsLogo", 100, 100);
//
//    run.setText("At tutorialspoint.com, we strive hard to " +
//            "provide quality tutorials for self-learning " +
//            "purpose in the domains of Academics, Information " +
//            "Technology, Management and Computer Programming Languages.");
//
//    //Set bottom border to paragraph
//    paragraph.setBorderBottom(Borders.BASIC_BLACK_DASHES);
//
//    //Set left border to paragraph
//    paragraph.setBorderLeft(Borders.BASIC_BLACK_DASHES);
//
//    //Set right border to paragraph
//    paragraph.setBorderRight(Borders.BASIC_BLACK_DASHES);
//
//    //Set top border to paragraph
//    paragraph.setBorderTop(Borders.BASIC_BLACK_DASHES);
//
//    //create table
//    XWPFTable table = document.createTable();
//
//    //create first row
//    XWPFTableRow tableRowOne = table.getRow(0);
//    tableRowOne.getCell(0).setText("col one, row one");
//    tableRowOne.addNewTableCell().setText("col two, row one");
//    tableRowOne.addNewTableCell().setText("col three, row one");
//
//    //create second row
//    XWPFTableRow tableRowTwo = table.createRow();
//    tableRowTwo.getCell(0).setText("col one, row two");
//    tableRowTwo.getCell(1).setText("col two, row two");
//    tableRowTwo.getCell(2).setText("col three, row two");
//
//    //create third row
//    XWPFTableRow tableRowThree = table.createRow();
//    tableRowThree.getCell(0).setText("col one, row three");
//    tableRowThree.getCell(1).setText("col two, row three");
//    tableRowThree.getCell(2).setText("col three, row three");
//
//    //Set Bold an Italic
//    XWPFRun paragraphOneRunOne = paragraph.createRun();
//    paragraphOneRunOne.addBreak();
//    paragraphOneRunOne.setBold(true);
//    paragraphOneRunOne.setItalic(true);
//    paragraphOneRunOne.setText("Font Style");
//    paragraphOneRunOne.addBreak();
//
//    //Set text Position
//    XWPFRun paragraphOneRunTwo = paragraph.createRun();
//    paragraphOneRunTwo.setText("Font Style two");
//    paragraphOneRunTwo.setTextPosition(100);
//
//    //Set Strike through and Font Size and Subscript
//    XWPFRun paragraphOneRunThree = paragraph.createRun();
//    paragraphOneRunThree.setStrike(true);
//    paragraphOneRunThree.setFontSize(20);
//    paragraphOneRunThree.setSubscript(VerticalAlign.SUBSCRIPT);
//    paragraphOneRunThree.setText(" Different Font Styles");
//    document.write(out);
//    out.close();
//    System.out.println("createdocument.docx written successully");
//  }
}