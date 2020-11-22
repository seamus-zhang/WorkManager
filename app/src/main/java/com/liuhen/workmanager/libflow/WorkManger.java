package com.liuhen.workmanager.libflow;

import android.util.Log;
import android.util.SparseArray;

/**
 * @author Seamus.Zhang
 * Create On 2020/11/21
 * Description : 工作节点管理工具
 */
public class WorkManger {

    private final String TAG = "WorkManger";

    private SparseArray<AbsNode> mNodeArray;
    private IWorkFinishLister mFinishListener;
    private boolean isFinish;

    private AbsNode currentNode;

    public WorkManger() {
        mNodeArray = new SparseArray<>();
    }

    public WorkManger(SparseArray<AbsNode> mNodeArray){
        this.mNodeArray = mNodeArray;
    }

    public void addNode(AbsNode mNode){
        this.mNodeArray.append(mNode.getId(),mNode);
    }

    public void setFinishListener(IWorkFinishLister mFinishListener){
        this.mFinishListener = mFinishListener;
    }

    public void start(){
        Log.d(TAG," current nodeArray size is "+mNodeArray.size());
        startWithId(mNodeArray.keyAt(0));
    }

    /**
     * 以指定节点开始
     * @param id 节点id
     */
    public void startWithId(int id ){
        if(isFinishState()){
            return;
        }

        AbsNode mNode = mNodeArray.get(id);
        if(mNode == null){
            Log.e(TAG,"startWithId error taskNode is null");
            return;
        }
        currentNode = mNode;
        mNode.doAction(new IActionCallBack() {
            @Override
            public void actionCompleted() {
                findAndExcuteNextNode();
            }
        });
    }



    private void findAndExcuteNextNode(){

        if(isNotStartOrFinish()){
            return;
        }

        final int currentIndex = mNodeArray.indexOfKey(currentNode.getId());
        final int nextIndex = currentIndex+ 1;
        if(nextIndex >= mNodeArray.size() || nextIndex<=0){
            Log.d(TAG,nextIndex+" at findAndExcuteNextNode  is the last one");
            if (nextIndex == mNodeArray.size() && mFinishListener != null) {
                mFinishListener.onWorkFinish();
            }
            return;
        }
        AbsNode nextTaskNode = mNodeArray.valueAt(nextIndex);
        if(nextTaskNode == null){
            throw new IllegalStateException("can not find next node ");
        }
        startWithId(nextTaskNode.getId());
    }

    /**
     * 继续下一个节点
     */
    public void continueWork(){
        if(isNotStartOrFinish()){
            return;
        }
        currentNode.finish();
    }

    /**
     * 执行上一个节点
     */
    public void revokeWork(){

        if(isNotStartOrFinish()){
            return;
        }
        int index = mNodeArray.indexOfValue(currentNode);
        int nextIndex = index -1;
        if(nextIndex < 0){
            Log.e(TAG," revoke error ,current is the first one ");
            return;
        }
        startWithId(mNodeArray.keyAt(nextIndex));

    }



    /**
     * 跳过下一个节点，继续往下执行
     */
    public void jumpOneContinue(){

        if(isNotStartOrFinish()){
            return;
        }
        int index = mNodeArray.indexOfValue(currentNode);
        int nextIndex = index + 2;
        if(nextIndex >= mNodeArray.size() ){
            Log.e(TAG," the jump one is out of the array size ,can not jump");
            return;
        }
        startWithId(mNodeArray.keyAt(nextIndex));

    }

    private boolean isNotStartOrFinish(){

        if(isFinish){
            Log.e(TAG,"current work is finish ,can't excute this method");
            return true;
        }

        if(currentNode == null){
            Log.e(TAG,"The work has not started or currentNode is null,can't excute this method");
            return true;
        }
        return false;
    }

    private boolean isFinishState(){
        if(isFinish){
            Log.e(TAG,"current work is finish ,can't excute this method");
            return true;
        }
        return false;
    }

    /**
     * 释放资源，建议在页面销毁时调用
     */
    public void onFinish(){
        isFinish = true;
        reset();
        if(mNodeArray != null){
            mNodeArray.clear();
        }
        if(mFinishListener != null){
            mFinishListener.onWorkFinish();
        }
    }

    private void reset(){
        if(mNodeArray!=null){
            for(int x=0;x<mNodeArray.size();x++){
                mNodeArray.valueAt(x).recycle();
            }
        }
    }


    public interface IWorkFinishLister{
        void onWorkFinish();
    }

    public static class Builder{

      private  WorkManger mWorkManger;
      private int autoNum;

      public  Builder (){
          mWorkManger = new WorkManger();
          autoNum = 0;
      }


        /**
         * 用此方法添加的节点node必须设置节点id的值，且必须大于0
         */
      public Builder addNode(AbsNode mNode){
          mWorkManger.addNode(mNode);
          return this;
      }

        /**
         * 添加自动设置id的工作节点
         */
      public Builder addAutoIdNode(AbsNode mNode){
          mNode.setId(++autoNum);
          mWorkManger.addNode(mNode);
          return this;
      }

      public Builder addFinishListener(IWorkFinishLister listener){
          mWorkManger.setFinishListener(listener);
          return this;
      }

      public WorkManger builder(){
          return mWorkManger;
      }

    }

}
