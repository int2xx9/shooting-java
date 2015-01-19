package shooting.example;

import javax.swing.JApplet;
import java.awt.Image;
import javax.imageio.ImageIO;
import java.net.URL;

/// JApplet.getDocumentBase()�Ŏ擾�ł���ꏊ����摜��ǂݍ��ރN���X
public class DocumentBaseImageLoader {
	private JApplet applet;

	/// �R���X�g���N�^
	/// @param applet JApplet�I�u�W�F�N�g
	public DocumentBaseImageLoader(JApplet applet) {
		this.applet = applet;
	}

	/// �摜�̎擾
	///
	/// JApplet.getImage(URL url)�̓���Ɠ��l
	/// @param url �擾��URL
	/// @return �摜
	public Image getImage(URL url) {
		return applet.getImage(url);
	}

	/// �摜�̎擾
	///
	/// JApplet.getImage(URL url, String name)�̓���Ɠ��l
	/// @param string �擾���t�@�C����(DocumentBase����̑��΃p�X)
	/// @return �摜
	public Image getImage(String name) {
		return applet.getImage(applet.getDocumentBase(), name);
	}

	/// �摜�̓ǂݍ���
	///
	/// getImage�Ƃ͈Ⴂ�����摜�̓��e��ǂݍ���
	/// ���̂��߁A���ۂ̕`��̑O�ł��Ԃ��Ă����摜��getWidth(), getHeight()�\
	/// @param url �擾��URL
	/// @return �摜
	public Image getImageImmediately(URL url) {
		try {
			return ImageIO.read(url);
		} catch (Exception e) {
			return null;
		}
	}

	/// �摜�̓ǂݍ���
	///
	/// getImage�Ƃ͈Ⴂ�����摜�̓��e��ǂݍ���
	/// ���̂��߁A���ۂ̕`��̑O�ł��Ԃ��Ă����摜��getWidth(), getHeight()�\
	/// @param string �擾���t�@�C����(DocumentBase����̑��΃p�X)
	/// @return �摜
	public Image getImageImmediately(String name) {
		try {
			return ImageIO.read(new URL(applet.getDocumentBase(), name));
		} catch (Exception e) {
			return null;
		}
	}
}

