package shooting.core;

public interface ShootingListener {
	public void onGameResumed();
	public void onGamePaused();
	public void onGameRestarted();
	public void onGameOvered();
}

