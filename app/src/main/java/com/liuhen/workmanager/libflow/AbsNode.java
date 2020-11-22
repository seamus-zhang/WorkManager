package com.liuhen.workmanager.libflow;

/**
 * @author Seamus.Zhang
 * Create On 2020/11/22
 * Description :
 */
public abstract class AbsNode implements INode{

    protected int mId = -1;
    protected IAction mAction;

    public AbsNode(IAction mAction) {
        this.mAction = mAction;
    }

    public AbsNode(int mId, IAction mAction) {
        this.mId = mId;
        this.mAction = mAction;
    }

    @Override
    public int getId() {
        if(mId < 0){
           throw  new IllegalArgumentException(" The id of node  can not be -1 ");
        }
        return mId;
    }

    public void setId(int mId){
        this.mId = mId;
    }

    @Override
    public void recycle(){
        if(mAction != null){
            mAction = null;
        }
        mId = -1;
    }
}
