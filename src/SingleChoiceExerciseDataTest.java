import android.content.Context;
import android.content.res.Resources;
import android.test.AndroidTestCase;

import com.gma.exerciser.R;
import com.gma.exerciser.LearningLogic.Exercises.ExerciseData;
import com.gma.exerciser.LearningLogic.Exercises.SingleChoiceExerciseData;
import com.gma.exerciser.Pojo.Word;
import com.gma.exerciser.test.DAO.MockDictionaryDAO;

import junit.framework.Assert;
import junit.framework.TestCase;

public class SingleChoiceExerciseDataTest extends AndroidTestCase {
	private Resources resources;
	private Context context;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.context = this.getContext();
		this.resources = this.context.getResources();
	}

	public void testGetNextExerciseData() { // test comment
		// gives a chunk of 10 dummy words like {"word1";"translation1"}
		MockDictionaryDAO mockDictDao = new MockDictionaryDAO();
		SingleChoiceExerciseData data = new SingleChoiceExerciseData(
				mockDictDao);
		SingleChoiceExerciseData resultData = (SingleChoiceExerciseData) data
				.getNextExerciseData(resources);

		Assert.assertEquals(resultData.getVariants().size(), this.resources
				.getInteger(R.integer.exercise_single_choice_variants_number));
		Assert.assertEquals(resultData.getWords().size(), 1);
		Assert.assertEquals(resultData.getClass(), data.getClass());
		Assert.assertEquals(data.getLength(), 1);
		Assert.assertEquals(resultData.getNumberOfErrors(), 0);
		Assert.assertEquals(resultData.getPoints(), 0);
		String correctAnswer = resultData.getWords().get(0)
				.getMain_translation();
		int incorrectIndex = resultData.getCorrectAnswerIndex() + 1;
		if (incorrectIndex == resultData.getVariants().size()) {
			incorrectIndex = 0;
		}
		String inCorrectAnswer = resultData.getVariants().get(incorrectIndex)
				.getMain_translation();
		String task = resultData.getWords().get(0).getWord();
		Assert.assertTrue(resultData.isTranslationCorrect(task, correctAnswer));
		Assert.assertFalse(resultData.isTranslationCorrect(task,
				inCorrectAnswer));

		Word askedWord = resultData.getVariants().get(
				resultData.getCorrectAnswerIndex());
		Assert.assertEquals(askedWord.getMain_translation(), resultData
				.getWords().get(0).getMain_translation());
	}

	public void testSetErrors() {
		fail("Not yet implemented");
	}

	public void testGetWordsTranslations() {
		fail("Not yet implemented");
	}

	public void testGetWordsNames() {
		fail("Not yet implemented");
	}

	public void testGetWordObjectFromTranslation() {
		fail("Not yet implemented");
	}

	public void testGetWordObjectFromName() {
		fail("Not yet implemented");
	}

	public void testRemoveWordByName() {
		fail("Not yet implemented");
	}

	public void testIsTranslationCorrect() {
		fail("Not yet implemented");
	}

}
