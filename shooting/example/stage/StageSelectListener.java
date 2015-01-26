package shooting.example.stage;

/// ステージ選択に関するイベントのリスナ
public interface StageSelectListener {
	/// ステージが選択された
	/// @param selectedStage 選ばれたステージ
	public void stageSelected(Stage selectedStage);
}

