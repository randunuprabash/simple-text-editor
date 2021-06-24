package Controller;

import javafx.event.ActionEvent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class EditFormController {
    public AnchorPane pneFind;
    public TextField txtSearch;
    public TextArea txtEditor;
    private int FindOffSet=-1;
    private int FindOffSet1=-1;
    private List<Index> searchList= new ArrayList<>();

    public void initialize(){
        pneFind.setVisible(false);

        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> {

            //FXUtil.highlightOnTextArea(txtEditor,newValue, Color.web("yellow", 0.8));

            try {
                Pattern regExp = Pattern.compile(newValue);
                Matcher matcher = regExp.matcher(txtEditor.getText());

                searchList.clear();

                while (matcher.find()) {
                    searchList.add(new Index(matcher.start(),matcher.end()));
                }
            } catch (PatternSyntaxException e) {

            }
        });
    }
    public void mnuItemNew_OnAction(ActionEvent actionEvent) {
    }

    public void mnuItemFind_OnAction(ActionEvent actionEvent) {
        pneFind.setVisible(true);
        txtSearch.requestFocus();
    }

    public void mnuItemReplace_OnAction(ActionEvent actionEvent) {
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
}

class Index{
    int startingIndex;
    int endIndex;
    public Index(int startingIndex, int endIndex){
        this.startingIndex = startingIndex;
        this.endIndex = endIndex;
    }
}