package com.vte.libgdx.ortho.test.quests;

import java.util.ArrayList;

/**
 * Created by vincent on 12/01/2017.
 */

public class Quest {
    protected boolean mIsActivated;
    protected boolean mIsCompleted;
    protected String id;
    protected ArrayList<String> requiredCompletedQuest;
    protected ArrayList<QuestTask> tasks;
    protected String startQuestDialogId;


    public String getId() {
        return id;
    }

    public void setId(String aId) {
        id = aId;
    }

    public void setActivated(boolean activated) {
        mIsActivated = activated;
    }

    public boolean isActivated() {
        return mIsActivated;

    }

    public void setCompleted(boolean completed) {
        mIsCompleted = completed;
    }

    public boolean isCompleted() {
        return mIsCompleted;

    }

    public ArrayList<String> getRequiredCompletedQuest() {
        return requiredCompletedQuest;
    }

    public void setRequiredCompletedQuest(ArrayList<String> aRequiredCompletedQuest) {
        requiredCompletedQuest = aRequiredCompletedQuest;
    }

    public ArrayList<QuestTask> getTasks() {
        return tasks;
    }

    public void setTasks(ArrayList<QuestTask> aTasks) {
        tasks = aTasks;
    }

    public String getStartQuestDialogId() {
        return startQuestDialogId;
    }

    public void setStartQuestDialogId(String aId) {
        startQuestDialogId = aId;
    }

    public void computeCompleted() {
        for (QuestTask task : tasks) {
            if (!task.isCompleted()) {
                mIsCompleted = false;
                return;
            }
        }
        mIsCompleted = true;

    }

    public boolean isTaskDependenciesCompleted(QuestTask task) {


        if (task.getRequiredCompletedTask() != null) {
            for (String taskId : task.getRequiredCompletedTask()) {
                for (QuestTask taskDenpendency : tasks) {
                    if (taskDenpendency.getId().equals(taskId) && !taskDenpendency.isCompleted()) {
                        return false;
                    }
                }
            }
            return true;
        } else {
            return false;
        }

    }

}
