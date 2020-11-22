# WorkManager
通过节点的方式管理多个任务执行顺序
使用方式：
 1，添加自动设置id的节点
  mWorkManger = new WorkManger.Builder()
                .addAutoIdNode(getFirstNode())
                .addAutoIdNode(getSecondNode())
                .addAutoIdNode(getThirdNode())
                .builder();
                
  private AbsNode getFirstNode(){
        return new FlowNode(new IAction() {
            @Override
            public void doAction(INode node) {
                AlertDialog dialog = createDialog("入目无别人，四下皆是你");
                dialog.show();
            }
        });
    }            
 2，添加自定义id的节点
 
   final static int FIRST_NODE_ID = 11;
   
   mWorkManger = new WorkManger.Builder()
                .addNode(getFirstNode())
                .addNode(getSecondNode())
                .addNode(getThirdNode())
                .builder();
                
  private AbsNode getFirstNode(){
        return new FlowNode(FIRST_NODE_ID,new IAction() {
            @Override
            public void doAction(INode node) {
                AlertDialog dialog = createDialog("入目无别人，四下皆是你");
                dialog.show();
            }
        });
    }         
