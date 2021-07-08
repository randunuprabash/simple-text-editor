package Controller;

import javafx.event.ActionEvent;
import javafx.print.PrinterJob;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class EditFormController {
    public AnchorPane pneFind;
    public TextField txtSearch;
    public TextArea txtEditor;
    public AnchorPane pneFind1;
    public TextField txtSearch1;
    public TextField txtReplace;
    private int FindOffSet=-1;
    private int FindOffSet1=-1;
    private int FindOffSet2=-1;
    private int FindOffSet3=-1;
    private List<Index> searchList= new ArrayList<>();
    private List<Index> searchList1= new ArrayList<>();
    private PrinterJob printerJob;
    private File fileSave;

    public void initialize(){
        pneFind.setVisible(false);
        pneFind1.setVisible(false);
        this.printerJob=PrinterJob.createPrinterJob();

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {
            //FXUtil.highlightOnTextArea(txtEditor,newValue, Color.web("yellow", 0.8));
            try {
                Pattern regExp = Pattern.compile(newValue);
                Matcher matcher = regExp.matcher(txtEditor.getText());
                searchList.clear();
                while (matcher.find()) {
                    searchList.add(new Index(matcher.start(),matcher.end()));
                }
            } catch (PatternSyntaxException e) {}
        });


        txtSearch1.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                Pattern regExp = Pattern.compile(newValue);
                Matcher matcher = regExp.matcher(txtEditor.getText());
                searchList1.clear();
                while (matcher.find()) {
                    searchList1.add(new Index(matcher.start(),matcher.end()));
                }
            } catch (PatternSyntaxException e) {}
        });
    }
    public void mnuItemNew_OnAction(ActionEvent actionEvent) {
    }

    public void mnuItemFind_OnAction(ActionEvent actionEvent) {
        pneFind.setVisible(true);
        pneFind1.setVisible(false);
        txtSearch.requestFocus();
    }

    public void mnuItemReplace_OnAction(ActionEvent actionEvent) {
        pneFind.setVisible(false);
        pneFind1.setVisible(true);
        txtSearch1.requestFocus();
    }

    public void btnFindNew_OnAction(ActionEvent actionEvent) {

        if (!searchList.isEmpty()) {
            if (FindOffSet == -1) {
                FindOffSet= 0;
            }
            txtEditor.selectRange(searchList.get(FindOffSet).startingIndex, searchList.get(FindOffSet).endIndex);
            FindOffSet1=FindOffSet-1;
            FindOffSet++;
            if (FindOffSet>= searchList.size()) {
                FindOffSet = 0;
            }
        }
    }


    public void btnFindPrevious_OnAction(ActionEvent actionEvent) {
        if(!searchList.isEmpty()){
            if(FindOffSet1==-1){
                FindOffSet1=searchList.size()-1;
            }

            txtEditor.selectRange(searchList.get(FindOffSet1).startingIndex,searchList.get(FindOffSet1).endIndex);
            FindOffSet=FindOffSet1+1;
            FindOffSet1--;
            if(FindOffSet1<0){
                FindOffSet1 =searchList.size()-1;
            }
        }
    }

    public void btnFindNew_OnAction1(ActionEvent actionEvent) {
        if (!searchList1.isEmpty()) {
            if (FindOffSet2 == -1) {
                FindOffSet2= 0;
            }
            txtEditor.selectRange(searchList1.get(FindOffSet2).startingIndex, searchList1.get(FindOffSet2).endIndex);
            FindOffSet3=FindOffSet2-1;
            FindOffSet2++;
            if (FindOffSet2>= searchList1.size()) {
                FindOffSet2 = 0;
            }
        }
    }

    public void btnFindPrevious_OnAction1(ActionEvent actionEvent) {
        if(!searchList1.isEmpty()){
            if(FindOffSet3==-1){
                FindOffSet3=searchList1.size()-1;
            }

            txtEditor.selectRange(searchList1.get(FindOffSet3).startingIndex,searchList1.get(FindOffSet3).endIndex);
            FindOffSet2=FindOffSet3+1;
            FindOffSet3--;
            if(FindOffSet3<0){
                FindOffSet3 =searchList.size()-1;
            }
        }
    }

    public void btnReplace_OnAction(ActionEvent actionEvent) {
           if(FindOffSet2!=0){
               int location=FindOffSet2-1;
               txtEditor.replaceText(searchList1.get(location).startingIndex,searchList1.get(location).endIndex,txtReplace.getText());
           }
    }

    public void btnReplaceAll_OnAction(ActionEvent actionEvent) {
        if(FindOffSet2!=0) {
            for (int i = 0; i < searchList1.size(); i++) {
                txtEditor.replaceText(searchList1.get(i).startingIndex, searchList1.get(i).endIndex, txtReplace.getText());
            }
        }
    }

    public void mnuItemOpen_OnAction(ActionEvent actionEvent) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Open File");
        fileChooser.getExtensionFilters().add
                (new FileChooser.ExtensionFilter("All Text Files","*.txt","*.html"));
        fileChooser.getExtensionFilters().add
                (new FileChooser.ExtensionFilter("All Files","*"));
        File file=fileChooser.showOpenDialog(txtEditor.getScene().getWindow());
        if(file==null)return;
        txtEditor.clear();
        try(FileReader fileReader=new FileReader(file);
            BufferedReader bufferedReader=new BufferedReader(fileReader)){
            String line=null;
            while ((line=bufferedReader.readLine())!=null){
                txtEditor.appendText(line+"\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void mnuItemSaveAs_OnAction(ActionEvent actionEvent) {
        FileChooser fileChooser=new FileChooser();
        fileChooser.setTitle("Save File");
        File file=fileChooser.showSaveDialog(txtEditor.getScene().getWindow());
        fileSave=file;
//        System.out.println(file);
        if(file==null)return;
        try (FileWriter fileWriter = new FileWriter(file);
             BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(txtEditor.getText());
        } catch (IOException e) {
           new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
        }
    }

    public void mnuItemPageSetup_OnAction(ActionEvent actionEvent) {
        printerJob.showPageSetupDialog(txtEditor.getScene().getWindow());
    }
    public void mnuItemPrint_OnAction(ActionEvent actionEvent) {
        printerJob.showPrintDialog(txtEditor.getScene().getWindow());
        printerJob.printPage(txtEditor.lookup("Text"));
    }

    public void mnuItemSave_OnAction(ActionEvent actionEvent) {
        if(fileSave==null){
            FileChooser fileChooser=new FileChooser();
            fileChooser.setTitle("Save File");
            File file=fileChooser.showSaveDialog(txtEditor.getScene().getWindow());
            fileSave=file;
            if(file==null)return;
            try (FileWriter fileWriter = new FileWriter(file);
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                bufferedWriter.write(txtEditor.getText());
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }else{
            try (FileWriter fileWriter = new FileWriter(fileSave);
                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
                bufferedWriter.write(txtEditor.getText());
            } catch (IOException e) {
                new Alert(Alert.AlertType.ERROR,e.getMessage()).show();
            }
        }

    }

    public void mnuItemCopy_OnAction(ActionEvent actionEvent) {
        txtEditor.copy();
    }

    public void mnuItemPaste_OnAction(ActionEvent actionEvent) {
        txtEditor.paste();
    }

    public void mnuItemSelectAll_OnAction(ActionEvent actionEvent) {
        txtEditor.selectAll();
    }

    public void mnuItemCut_OnAction(ActionEvent actionEvent) {
        txtEditor.cut();
    }
}

class Index{
    int startingIndex;
    int endIndex;
    public Index(int startingIndex, int endIndex){
        this.startingIndex = startingIndex;
        this.endIndex = endIndex;
    }
}
