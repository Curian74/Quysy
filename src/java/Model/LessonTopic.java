/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Admin
 */
public class LessonTopic {

    private int lessonTopicId;
    private String lessonTopicName;

    public LessonTopic()    {

    }

    public LessonTopic(int lessonTopicId, String lessonTopicName) {
        this.lessonTopicId = lessonTopicId;
        this.lessonTopicName = lessonTopicName;
    }

    public int getLessonTopicId() {
        return lessonTopicId;
    }

    public void setLessonTopicId(int lessonTopicId) {
        this.lessonTopicId = lessonTopicId;
    }

    public String getLessonTopicName() {
        return lessonTopicName;
    }

    public void setLessonTopicName(String lessonTopicName) {
        this.lessonTopicName = lessonTopicName;
    }

    @Override
    public String toString() {
        return "LessonTopic{" + "lessonTopicId=" + lessonTopicId + ", lessonTopicName=" + lessonTopicName + '}';
    }

}
