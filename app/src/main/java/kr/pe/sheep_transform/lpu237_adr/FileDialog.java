package kr.pe.sheep_transform.lpu237_adr;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Environment;
import androidx.appcompat.app.AlertDialog;//import android.support.v7.app.AlertDialog;
import android.util.Log;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

public class FileDialog {
    private static final String PARENT_DIR = "..";
    private final String TAG = getClass().getName();
    private String[] m_fileList;
    private File m_currentPath;
    public interface FileSelectedListener {
        void fileSelected(File file);
    }
    public interface DirectorySelectedListener {
        void directorySelected(File directory);
    }
    private ListenerList<FileSelectedListener> m_fileListenerList = new ListenerList<FileDialog.FileSelectedListener>();
    private ListenerList<DirectorySelectedListener> m_dirListenerList = new ListenerList<FileDialog.DirectorySelectedListener>();
    private final Activity m_activity;
    private boolean m_selectDirectoryOption = false;
    private String m_fileEndsWith;
    private DialogInterface.OnCancelListener m_listener_cancel;

    /**
     * @param activity
     * @param initialPath
     */
    public FileDialog(Activity activity, File initialPath) {
        this(activity, initialPath, null);
    }

    public FileDialog(Activity activity, File initialPath, String fileEndsWith) {
        this.m_activity = activity;
        setFileEndsWith(fileEndsWith);

        if (!initialPath.exists())
            initialPath = Environment.getExternalStorageDirectory();

        loadFileList(initialPath);
    }

    /**
     * @return file dialog
     */
    public Dialog createFileDialog() {
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(m_activity);

        builder.setTitle(m_currentPath.getPath());
        if (m_selectDirectoryOption) {
            builder.setPositiveButton("Select directory", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(TAG, m_currentPath.getPath());
                    fireDirectorySelectedEvent(m_currentPath);
                }
            });
        }

        builder.setItems(m_fileList, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String fileChosen = m_fileList[which];
                File chosenFile = getChosenFile(fileChosen);
                if (chosenFile.isDirectory()) {
                    loadFileList(chosenFile);
                    dialog.cancel();
                    dialog.dismiss();
                    showDialog();
                } else fireFileSelectedEvent(chosenFile);
            }
        });

        dialog = builder.show();
        return dialog;
    }
    public Dialog createFileDialog(DialogInterface.OnCancelListener listener_cancel) {
        m_listener_cancel = listener_cancel;
        Dialog dialog = null;
        AlertDialog.Builder builder = new AlertDialog.Builder(m_activity);

        builder.setTitle(m_currentPath.getPath());
        if (m_selectDirectoryOption) {
            builder.setPositiveButton("Select directory", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    Log.d(TAG, m_currentPath.getPath());
                    fireDirectorySelectedEvent(m_currentPath);
                }
            });
        }

        builder.setItems(m_fileList, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String fileChosen = m_fileList[which];
                File chosenFile = getChosenFile(fileChosen);
                if (chosenFile.isDirectory()) {
                    loadFileList(chosenFile);
                    //dialog.cancel();
                    dialog.dismiss();
                    showDialog(FileDialog.this.m_listener_cancel);
                } else fireFileSelectedEvent(chosenFile);
            }
        });

        builder.setOnCancelListener(listener_cancel);

        dialog = builder.show();
        return dialog;
    }


    public void addFileListener(FileSelectedListener listener) {
        m_fileListenerList.add(listener);
    }

    public void removeFileListener(FileSelectedListener listener) {
        m_fileListenerList.remove(listener);
    }

    public void setSelectDirectoryOption(boolean selectDirectoryOption) {
        this.m_selectDirectoryOption = selectDirectoryOption;
    }

    public void addDirectoryListener(DirectorySelectedListener listener) {
        m_dirListenerList.add(listener);
    }

    public void removeDirectoryListener(DirectorySelectedListener listener) {
        m_dirListenerList.remove(listener);
    }

    /**
     * Show file dialog
     */
    public void showDialog() {
        createFileDialog().show();
    }
    public void showDialog(DialogInterface.OnCancelListener listener_cancel) {
        createFileDialog(listener_cancel).show();
    }

    private void fireFileSelectedEvent(final File file) {
        m_fileListenerList.fireEvent(new ListenerList.FireHandler<FileSelectedListener>() {
            public void fireEvent(FileSelectedListener listener) {
                listener.fileSelected(file);
            }
        });
    }

    private void fireDirectorySelectedEvent(final File directory) {
        m_dirListenerList.fireEvent(new ListenerList.FireHandler<DirectorySelectedListener>() {
            public void fireEvent(DirectorySelectedListener listener) {
                listener.directorySelected(directory);
            }
        });
    }

    private void loadFileList(File path) {
        this.m_currentPath = path;
        List<String> r = new ArrayList<String>();
        if (path.exists()) {
            if (path.getParentFile() != null)
                r.add(PARENT_DIR);
            //
            FilenameFilter filter = new FilenameFilter() {
                public boolean accept(File dir, String filename) {
                    File sel = new File(dir, filename);
                    boolean b_accept = false;

                    do{
                        if( !sel.canRead())
                            continue;
                        if (m_selectDirectoryOption){
                            b_accept = sel.isDirectory();
                            continue;
                        }

                        boolean b_endsWith = true;

                        if( m_fileEndsWith != null ){
                            b_endsWith = filename.toLowerCase().endsWith(m_fileEndsWith);
                        }

                        b_accept = b_endsWith || sel.isDirectory();

                    }while(false);

                    return b_accept;
                }
            };

            String[] fileList1 = path.list(filter);
            if( fileList1 != null ) {
                for (String file : fileList1) {
                    r.add(file);
                }
            }
        }
        m_fileList = (String[]) r.toArray(new String[]{});
    }

    private File getChosenFile(String fileChosen) {
        if (fileChosen.equals(PARENT_DIR))
            return m_currentPath.getParentFile();
        else
            return new File(m_currentPath, fileChosen);
    }

    private void setFileEndsWith(String fileEndsWith) {
        this.m_fileEndsWith = fileEndsWith != null ? fileEndsWith.toLowerCase() : fileEndsWith;
    }
}

class ListenerList<L> {
    private List<L> listenerList = new ArrayList<L>();

    public interface FireHandler<L> {
        void fireEvent(L listener);
    }

    public void add(L listener) {
        listenerList.add(listener);
    }

    public void fireEvent(FireHandler<L> fireHandler) {
        List<L> copy = new ArrayList<L>(listenerList);
        for (L l : copy) {
            fireHandler.fireEvent(l);
        }
    }

    public void remove(L listener) {
        listenerList.remove(listener);
    }

    public List<L> getListenerList() {
        return listenerList;
    }
}

