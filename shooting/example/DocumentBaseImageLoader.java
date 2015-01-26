package shooting.example;

import javax.swing.JApplet;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.net.URL;

/// JApplet.getDocumentBase()で取得できる場所から画像を読み込むクラス
public class DocumentBaseImageLoader {
	private JApplet applet;

	/// コンストラクタ
	/// @param applet JAppletオブジェクト
	public DocumentBaseImageLoader(JApplet applet) {
		this.applet = applet;
	}

	/// 画像の取得
	///
	/// JApplet.getImage(URL url)の動作と同様
	/// @param url 取得元URL
	/// @return 画像
	public Image getImage(URL url) {
		return applet.getImage(url);
	}

	/// 画像の取得
	///
	/// JApplet.getImage(URL url, String name)の動作と同様
	/// @param string 取得元ファイル名(DocumentBaseからの相対パス)
	/// @return 画像
	public Image getImage(String name) {
		return applet.getImage(applet.getDocumentBase(), name);
	}

	/// 画像の読み込み
	///
	/// getImageとは違い即時画像の内容を読み込む
	/// そのため、実際の描画の前でも返ってきた画像でgetWidth(), getHeight()可能
	/// @param url 取得元URL
	/// @return 画像
	public Image getImageImmediately(URL url) {
		try {
			return ImageIO.read(url);
		} catch (Exception e) {
			return null;
		}
	}

	/// 画像の読み込み
	///
	/// getImageとは違い即時画像の内容を読み込む
	/// そのため、実際の描画の前でも返ってきた画像でgetWidth(), getHeight()可能
	/// @param string 取得元ファイル名(DocumentBaseからの相対パス)
	/// @return 画像
	public Image getImageImmediately(String name) {
		try {
			return ImageIO.read(new URL(applet.getDocumentBase(), name));
		} catch (Exception e) {
			return null;
		}
	}
}

