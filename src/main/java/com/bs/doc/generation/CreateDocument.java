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
}