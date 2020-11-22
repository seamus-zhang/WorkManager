package com.liuhen.workmanager;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.liuhen.workmanager.libflow.AbsNode;
import com.liuhen.workmanager.libflow.FlowNode;
import com.liuhen.workmanager.libflow.IAction;
import com.liuhen.workmanager.libflow.INode;
import com.liuhen.workmanager.libflow.WorkManger;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


/**
 * @author Seamus.Zhang
 * Create On 2020/11/22
 * Description :
 */
public class MainActivity extends AppCompatActivity {

    Context mContext;
    String mDialogTitle = "优美句子赏析";
    WorkManger mWorkManger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;
        initWorkManager();


        Button mButton = this.findViewById(R.id.button);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mWorkManger.start();
            }
        });

    }


    private void initWorkManager(){
        mWorkManger = new WorkManger.Builder()
                .addAutoIdNode(getFirstNode())
                .addAutoIdNode(getSecondNode())
                .addAutoIdNode(getThirdNode())
                .builder();
    }

    private AlertDialog createDialog(String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle(mDialogTitle);
        builder.setMessage(message);
        builder.setPositiveButton("我知道了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                mWorkManger.continueWork();
            }
        });
        builder.setCancelable(false);
       return builder.create();
    }

    private AbsNode getFirstNode(){
        return new FlowNode(new IAction() {
            @Override
            public void doAction(INode node) {
                AlertDialog dialog = createDialog("入目无别人，四下皆是你");
                dialog.show();
            }
        });
    }

    private AbsNode getSecondNode(){
        return new FlowNode(new IAction() {
            @Override
            public void doAction(INode node) {
                AlertDialog dialog = createDialog("我见众生皆草木，唯有见你是青山");
                dialog.show();
            }
        });
    }

    private AbsNode getThirdNode(){
        return new FlowNode(new IAction() {
            @Override
            public void doAction(INode node) {
                AlertDialog dialog = createDialog("原有岁月可回首，且以深情共白头");
                dialog.show();
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mWorkManger != null){
            mWorkManger.onFinish();
        }
    }
}