package com.gma.exerciser.test.LearningLogic;

import junit.framework.Assert;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.test.ActivityInstrumentationTestCase2;
import android.test.AndroidTestCase;

import com.gma.exerciser.MainMenu;
import com.gma.exerciser.R;
import com.gma.exerciser.SingleChoiceExercise;
import com.gma.exerciser.LearningLogic.LessonLogic;
import com.gma.exerciser.LearningLogic.Exercises.ExerciseData;
import com.gma.exerciser.LearningLogic.Exercises.MatchExerciseData;
import com.gma.exerciser.LearningLogic.Exercises.SingleChoiceExerciseData;
import com.gma.exerciser.Pojo.Word;

public class LessonLogicTest extends  AndroidTestCase {

	public LessonLogicTest() {
		super();
		// TODO Auto-generated constructor stub
	}

	private LessonLogic lessonLogic;
	private Context context;
	private SharedPreferences preferences;
	private Resources resources;
	
	private String EXERCISE_NUMBER = "exercise_number";
	private String CORRECT_ANSWERS = "correct_answers";
	private String INCORRECT_ANSWERS = "incorrect_answers";
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.context = this.getContext();
		Assert.assertNotNull(this.context);
		this.preferences = this.context.getSharedPreferences("lessonTest", 0);
		this.resources = this.context.getResources();
		this.lessonLogic = LessonLogic.getInstance(
				this.context.getApplicationContext(),
				this.preferences,
				this.resources);
	}

	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testStartLesson() {
		this.lessonLogic.startLesson();
		Assert.assertEquals(this.preferences.getInt(EXERCISE_NUMBER, -1), 0);
		Assert.assertEquals(this.preferences.getInt(CORRECT_ANSWERS, -1), 0);
		Assert.assertEquals(this.preferences.getInt(INCORRECT_ANSWERS, -1), 0);
	}
	
	public void testHandleError() {
		Editor editor = this.preferences.edit();
		editor.putInt(INCORRECT_ANSWERS, 5);
		editor.commit();
		Assert.assertEquals(this.preferences.getInt(INCORRECT_ANSWERS, -1), 5);
		this.lessonLogic.handleError();
		Assert.assertEquals(this.preferences.getInt(INCORRECT_ANSWERS, -1), 6);
	}
	
	public void  testGetNextExercise() {
		int exerciseNumber = this.preferences.getInt(EXERCISE_NUMBER, -1);
		Assert.assertEquals(exerciseNumber, 0);
		ExerciseData data = new SingleChoiceExerciseData();
		
		ExerciseData newData = this.lessonLogic.getNextExercise(data);
		
		int newExerciseNumber = this.preferences.getInt(EXERCISE_NUMBER, -1);
		Assert.assertEquals(exerciseNumber+1, newExerciseNumber);
		assertEquals(data.getClass(), newData.getClass());
	}
	
	public void testIsLessonFinished() {
		int exerciseInLesson = this.resources.getInteger(R.integer.exercise_in_lesson);
		Editor editor = this.preferences.edit();
		editor.putInt(EXERCISE_NUMBER, exerciseInLesson-1);
		editor.commit();
		
		boolean lessonFinished = this.lessonLogic.isLessonFinished();
		Assert.assertFalse(lessonFinished);
		
		editor = this.preferences.edit();
		editor.putInt(EXERCISE_NUMBER, exerciseInLesson);
		editor.commit();
		
		lessonFinished = this.lessonLogic.isLessonFinished();
		Assert.assertTrue(lessonFinished);
	}
	
	public void testSubmitExercise() {
		Editor editor = this.preferences.edit();
		editor.putInt(CORRECT_ANSWERS, 5);
		editor.commit();
		ExerciseData data = new SingleChoiceExerciseData();
		data.getWords().add(new Word());
		data.getWords().add(new Word());
		
		this.lessonLogic.submitExercise(data);
		Assert.assertEquals(this.preferences.getInt(CORRECT_ANSWERS, -1), 5 + data.getLength());
		
		editor = this.preferences.edit();
		editor.putInt(CORRECT_ANSWERS, 5);
		editor.commit();
		data = new MatchExerciseData();
		data.getWords().add(new Word());
		data.getWords().add(new Word());
		
		this.lessonLogic.submitExercise(data);		
		Assert.assertEquals(this.preferences.getInt(CORRECT_ANSWERS, -1), 5 + data.getLength());		
	}

}
