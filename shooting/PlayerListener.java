package shooting;

public interface PlayerListener {
	public void scoreUpdated();
	public void damageUpdated();
	public void comboUpdated();
	public void hitCountUpdated();
	public void notHitCountUpdated();
	public void playerDestroyed();
}

