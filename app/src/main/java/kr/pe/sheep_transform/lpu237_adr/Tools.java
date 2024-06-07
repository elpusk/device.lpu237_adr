package kr.pe.sheep_transform.lpu237_adr;

import static androidx.core.app.ActivityCompat.startActivityForResult;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.OpenableColumns;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;

import static java.util.Arrays.copyOf;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import kr.pe.sheep_transform.lpu237_adr.lib.util.KeyboardConst;
import kr.pe.sheep_transform.lpu237_adr.lib.util.MapCodeToString;



public class Tools {

    static public boolean is_all_zeros( byte[] s_data ){
        boolean b_zero = false;

        do{
            if( s_data == null )
                continue;

            b_zero = true;

            for( byte c : s_data){
                if( c != 0 ){
                    b_zero = false;
                    break;
                }
            }//end for
        }while(false);
        return b_zero;
    }

    static public IntentFilter getIntentFilter( int n_actions ) {
        IntentFilter filter = new IntentFilter();
        if( (n_actions & ManagerIntentAction.INT_USB_ATTACHED) != 0 )
            filter.addAction(ManagerIntentAction.USB_ATTACHED);
        if( (n_actions & ManagerIntentAction.INT_USB_DETACHED) != 0 )
            filter.addAction(ManagerIntentAction.USB_DETACHED);
        if( (n_actions & ManagerIntentAction.INT_UPDATE_LIST_NO_DEVICE) != 0 )
            filter.addAction(ManagerIntentAction.UPDATE_LIST_NO_DEVICE);

        if( (n_actions & ManagerIntentAction.INT_LPU237_PERMISSION) != 0 )
            filter.addAction(ManagerIntentAction.LPU237_PERMISSION);
        if( (n_actions & ManagerIntentAction.INT_GET_INFO_FOR_LIST) != 0 )
            filter.addAction(ManagerIntentAction.GET_INFO_FOR_LIST);
        if( (n_actions & ManagerIntentAction.INT_UPDATE_UID) != 0 )
            filter.addAction(ManagerIntentAction.UPDATE_UID);
        if( (n_actions & ManagerIntentAction.INT_GET_PARAMETERS) != 0 )
            filter.addAction(ManagerIntentAction.GET_PARAMETERS);
        if( (n_actions & ManagerIntentAction.INT_SET_PARAMETERS) != 0 )
            filter.addAction(ManagerIntentAction.SET_PARAMETERS);

        if( (n_actions & ManagerIntentAction.INT_BOOTLOADER_PERMISSION) != 0 )
            filter.addAction(ManagerIntentAction.BOOTLOADER_PERMISSION);
        if( (n_actions & ManagerIntentAction.INT_START_BOOTLOADER) != 0 )
            filter.addAction(ManagerIntentAction.START_BOOTLOADER);

        if( (n_actions & ManagerIntentAction.INT_SECTOR_INFO) != 0 )
            filter.addAction(ManagerIntentAction.SECTOR_INFO);
        if( (n_actions & ManagerIntentAction.INT_ERASE_SECTOR) != 0 )
            filter.addAction(ManagerIntentAction.ERASE_SECTOR);
        if( (n_actions & ManagerIntentAction.INT_ERASE_COMPLETE) != 0 )
            filter.addAction(ManagerIntentAction.ERASE_COMPLETE);
        if( (n_actions & ManagerIntentAction.INT_WRITE_SECTOR) != 0 )
            filter.addAction(ManagerIntentAction.WRITE_SECTOR);
        if( (n_actions & ManagerIntentAction.INT_WRITE_COMPLETE) != 0 )
            filter.addAction(ManagerIntentAction.WRITE_COMPLETE);
        if( (n_actions & ManagerIntentAction.INT_START_APP) != 0 )
            filter.addAction(ManagerIntentAction.START_APP);
        if( (n_actions & ManagerIntentAction.INT_RECOVER_PARAMETER) != 0 )
            filter.addAction(ManagerIntentAction.RECOVER_PARAMETER);

        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_STARTUP_DISPLAY_DEVICE_LIST) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_STARTUP_DISPLAY_DEVICE_LIST);

        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_MAIN_COMPLETE_GET_PARAMETERS) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_MAIN_COMPLETE_GET_PARAMETERS);
        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_MAIN_COMPLETE_SET_PARAMETERS) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_MAIN_COMPLETE_SET_PARAMETERS);
        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_MAIN_START_BOOT) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_MAIN_START_BOOT);

        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_START_BOOT) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_START_BOOT);
        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_SECTOR_INFO) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_SECTOR_INFO);
        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_ERASE_SECTOR) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_ERASE_SECTOR);
        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_DETAIL_ERASE_INFO) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_DETAIL_ERASE_INFO);
        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_ERASE_FIRMWARE) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_ERASE_FIRMWARE);

        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_WRITE_SECTOR) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_WRITE_SECTOR);
        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_DETAIL_WRITE_INFO) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_DETAIL_WRITE_INFO);
        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_WRITE_FIRMWARE) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_WRITE_FIRMWARE);

        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_START_APP) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_START_APP);
        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_GET_PARAMETERS) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_GET_PARAMETERS);
        if( (n_actions & ManagerIntentAction.INT_ACTIVITY_UPDATE_RECOVER_PARAMETER) != 0 )
            filter.addAction(ManagerIntentAction.ACTIVITY_UPDATE_RECOVER_PARAMETER);

        if( (n_actions & ManagerIntentAction.INT_GENERAL_TERMINATE_APP) != 0 )
            filter.addAction(ManagerIntentAction.GENERAL_TERMINATE_APP);
        return filter;
    }

    static public int getActionIntFromActionString( String s_action ){
        int n_action = ManagerIntentAction.INT_UNKNOWN;

        do{
            if( s_action == null )
                continue;
            if( s_action.equals( ManagerIntentAction.LPU237_PERMISSION )){
                n_action = ManagerIntentAction.INT_LPU237_PERMISSION;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.USB_ATTACHED)){
                n_action = ManagerIntentAction.INT_USB_ATTACHED;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.USB_DETACHED)){
                n_action = ManagerIntentAction.INT_USB_DETACHED;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.UPDATE_LIST_NO_DEVICE)){
                n_action = ManagerIntentAction.INT_UPDATE_LIST_NO_DEVICE;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.GET_INFO_FOR_LIST)){
                n_action = ManagerIntentAction.INT_GET_INFO_FOR_LIST;
                continue;
            }

            if( s_action.equals( ManagerIntentAction.BOOTLOADER_PERMISSION)){
                n_action = ManagerIntentAction.INT_BOOTLOADER_PERMISSION;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.START_BOOTLOADER)){
                n_action = ManagerIntentAction.INT_START_BOOTLOADER;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.SECTOR_INFO)){
                n_action = ManagerIntentAction.INT_SECTOR_INFO;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ERASE_SECTOR)){
                n_action = ManagerIntentAction.INT_ERASE_SECTOR;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ERASE_COMPLETE)){
                n_action = ManagerIntentAction.INT_ERASE_COMPLETE;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.UPDATE_UID)){
                n_action = ManagerIntentAction.INT_UPDATE_UID;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.GET_PARAMETERS)){
                n_action = ManagerIntentAction.INT_GET_PARAMETERS;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.SET_PARAMETERS)){
                n_action = ManagerIntentAction.INT_SET_PARAMETERS;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.WRITE_SECTOR)){
                n_action = ManagerIntentAction.INT_WRITE_SECTOR;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.WRITE_COMPLETE)){
                n_action = ManagerIntentAction.INT_WRITE_COMPLETE;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.START_APP)){
                n_action = ManagerIntentAction.INT_START_APP;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.RECOVER_PARAMETER)) {
                n_action = ManagerIntentAction.INT_RECOVER_PARAMETER;
                continue;
            }


            if( s_action.equals( ManagerIntentAction.ACTIVITY_STARTUP_DISPLAY_DEVICE_LIST)){
                n_action = ManagerIntentAction.INT_ACTIVITY_STARTUP_DISPLAY_DEVICE_LIST;
                continue;
            }

            if( s_action.equals( ManagerIntentAction.ACTIVITY_MAIN_COMPLETE_GET_PARAMETERS)){
                n_action = ManagerIntentAction.INT_ACTIVITY_MAIN_COMPLETE_GET_PARAMETERS;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ACTIVITY_MAIN_COMPLETE_SET_PARAMETERS)){
                n_action = ManagerIntentAction.INT_ACTIVITY_MAIN_COMPLETE_SET_PARAMETERS;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ACTIVITY_MAIN_START_BOOT)){
                n_action = ManagerIntentAction.INT_ACTIVITY_MAIN_START_BOOT;
                continue;
            }
            //
            if( s_action.equals( ManagerIntentAction.ACTIVITY_UPDATE_START_BOOT)){
                n_action = ManagerIntentAction.INT_ACTIVITY_UPDATE_START_BOOT;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_ERASE_SECTOR)){
                n_action = ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_ERASE_SECTOR;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ACTIVITY_UPDATE_DETAIL_ERASE_INFO)){
                n_action = ManagerIntentAction.INT_ACTIVITY_UPDATE_DETAIL_ERASE_INFO;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_ERASE_FIRMWARE)){
                n_action = ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_ERASE_FIRMWARE;
                continue;
            }

            if( s_action.equals( ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_WRITE_SECTOR)){
                n_action = ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_WRITE_SECTOR;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ACTIVITY_UPDATE_DETAIL_WRITE_INFO)){
                n_action = ManagerIntentAction.INT_ACTIVITY_UPDATE_DETAIL_WRITE_INFO;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_WRITE_FIRMWARE)){
                n_action = ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_WRITE_FIRMWARE;
                continue;
            }

            if( s_action.equals( ManagerIntentAction.ACTIVITY_UPDATE_START_APP)){
                n_action = ManagerIntentAction.INT_ACTIVITY_UPDATE_START_APP;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ACTIVITY_UPDATE_COMPLETE_GET_PARAMETERS)){
                n_action = ManagerIntentAction.INT_ACTIVITY_UPDATE_COMPLETE_GET_PARAMETERS;
                continue;
            }
            if( s_action.equals( ManagerIntentAction.ACTIVITY_UPDATE_RECOVER_PARAMETER)){
                n_action = ManagerIntentAction.INT_ACTIVITY_UPDATE_RECOVER_PARAMETER;
                continue;
            }
            //
            if( s_action.equals( ManagerIntentAction.GENERAL_TERMINATE_APP)){
                n_action = ManagerIntentAction.INT_GENERAL_TERMINATE_APP;
                continue;
            }
        }while(false);
        return n_action;
    }

    static public AlertDialog showYesNoDialog(
            Context context
            ,String s_title
            ,String s_message
            ,DialogInterface.OnClickListener listener_yes
            ,DialogInterface.OnClickListener listener_no
            ,DialogInterface.OnCancelListener listener_cancel
    )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(s_title);
        builder.setMessage(s_message);
        builder.setPositiveButton("YES", listener_yes);
        builder.setNegativeButton("NO",listener_no);
        builder.setOnCancelListener(listener_cancel);
        return builder.show();
    }
    static public void showOkDialog(
            Context context
            ,String s_title
            ,String s_message
            ,DialogInterface.OnClickListener listener_ok
            ,DialogInterface.OnCancelListener listener_cancel
    )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(s_title);
        builder.setMessage(s_message);
        builder.setNeutralButton("OK",listener_ok);
        builder.setOnCancelListener(listener_cancel);
        builder.show();
    }

    static private DialogInterface.OnClickListener m_listener_dlg_ok_error_terminate = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            // this listener is OK.
            dialog.dismiss();

            //terminate app
            ManagerDevice.getInstance().unload();

            if( ManagerDevice.getInstance().get_update_activity() != null ) {
                ManagerDevice.getInstance().get_update_activity().finishAffinity();
                ManagerDevice.getInstance().set_update_activity(null);
            }

            if( ManagerDevice.getInstance().get_main_activity() != null ) {
                ManagerDevice.getInstance().get_main_activity().finishAffinity();
                ManagerDevice.getInstance().set_main_activity(null);
            }
            if( ManagerDevice.getInstance().get_startup_activity() != null ) {
                ManagerDevice.getInstance().get_startup_activity().finishAffinity();
                ManagerDevice.getInstance().set_startup_activiy(null);
            }
            System.runFinalization();
            System.exit(0);

        }
    };
    static private DialogInterface.OnCancelListener m_listener_dlg_cancel_error_terminate = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            // when firmware update ok.
            dialog.dismiss();

            //terminate app
            ManagerDevice.getInstance().unload();
            if( ManagerDevice.getInstance().get_update_activity() != null ) {
                ManagerDevice.getInstance().get_update_activity().finishAffinity();
                ManagerDevice.getInstance().set_update_activity(null);
            }
            if( ManagerDevice.getInstance().get_main_activity() != null ) {
                ManagerDevice.getInstance().get_main_activity().finishAffinity();
                ManagerDevice.getInstance().set_main_activity(null);
            }
            if( ManagerDevice.getInstance().get_startup_activity() != null ) {
                ManagerDevice.getInstance().get_startup_activity().finishAffinity();
                ManagerDevice.getInstance().set_startup_activiy(null);
            }
            System.runFinalization();
            System.exit(0);
        }
    };

    static public void showOkDialogForErrorTerminate(
            Context context
            ,String s_flow
            ,String s_title
            ,String s_message
    )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(s_title+" - "+s_flow);
        builder.setMessage(s_message);
        builder.setNeutralButton("OK",Tools.m_listener_dlg_ok_error_terminate);
        builder.setOnCancelListener(Tools.m_listener_dlg_cancel_error_terminate);
        builder.show();
    }

    static private DialogInterface.OnClickListener m_listener_dlg_ok_error = new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) {
            // this listener is OK.
            dialog.dismiss();
        }
    };
    static private DialogInterface.OnCancelListener m_listener_dlg_cancel_error = new DialogInterface.OnCancelListener() {
        @Override
        public void onCancel(DialogInterface dialog) {
            // when firmware update ok.
            dialog.dismiss();
        }
    };

    static public void showOkDialogForError(
            Context context
            ,String s_flow
            ,String s_title
            ,String s_message
    )
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setCancelable(true);
        builder.setTitle(s_title+" - "+s_flow);
        builder.setMessage(s_message);
        builder.setNeutralButton("OK",Tools.m_listener_dlg_ok_error);
        builder.setOnCancelListener(Tools.m_listener_dlg_cancel_error);
        builder.show();
    }

    static public void selectFirmwareGreaterThenEqualApi29(Activity activity){
        //open file picker
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        //intent.setType("application/rom");
        intent.setType("*/*");

        // Optionally, specify a URI for the file that should appear in the
        // system file picker when it loads.
        String pickerInitialUri = "Download";
        intent.putExtra(DocumentsContract.EXTRA_INITIAL_URI, pickerInitialUri);

        startActivityForResult(activity, intent, IntentRequestCode.OPEN_ROM_FILE, null);

    }
    static public void selectFirmwareLessApi29(Activity activity,FileDialog.FileSelectedListener listener){
        do{
            // 현재 API 레벨이 29 미만인 경우 실행할 코드
            String s_legacyPath = System.getenv("EXTERNAL_STORAGE");
            String s_secondaryPath = System.getenv("SECONDARY_STORAGE");

            File mPath = new File(Environment.getExternalStorageDirectory() + "//");
            //File mPath = new File(s_legacyPath + "//");

            //FileDialog fileDialog = new FileDialog(activity, mPath, null);
            FileDialog fileDialog = new FileDialog(activity, mPath, ".rom");
            fileDialog.addFileListener(listener);

            fileDialog.showDialog();

        }while(false);
    }
    static public void selectFirmwareLessApi29_with_cancel(
            Activity activity
            ,FileDialog.FileSelectedListener listener
            ,DialogInterface.OnCancelListener listener_cancel
    )
    {
        do{
            String s_legacyPath = System.getenv("EXTERNAL_STORAGE");
            String s_secondaryPath = System.getenv("SECONDARY_STORAGE");

            File mPath = new File(Environment.getExternalStorageDirectory() + "//");
            //File mPath = new File(s_legacyPath + "//");
            FileDialog fileDialog = new FileDialog(activity, mPath, ".rom");
            //FileDialog fileDialog = new FileDialog(activity, mPath, null);
            fileDialog.addFileListener(listener);

            fileDialog.showDialog(listener_cancel);

        }while(false);
    }

    static public void start_main_activity( Context context ){
        if( context != null ) {
            Intent intent = new Intent(context, MainActivity.class);
            context.startActivity(intent);
        }
    }
    static public void start_update_activity( Context context ){
        if( context != null ) {
            Intent intent = new Intent(context, UpdateActivity.class);
            context.startActivity(intent);
        }
    }

    static public int get_app_version_code(Context context){
        PackageInfo packageInfo = null;

        //PackageInfo 초기화
        try{
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
            return -1;
        }

        return packageInfo.versionCode;
    }

    static public String get_app_version_name(Context context){
        PackageInfo packageInfo = null;         //패키지에 대한 전반적인 정보

        //PackageInfo 초기화
        try{
            packageInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
        }catch (PackageManager.NameNotFoundException e){
            e.printStackTrace();
            return "";
        }

        return packageInfo.versionName;
    }

    public static File fileFromContentUri(Activity act, Uri contentUri) {
        Context context = (Context)act;
        Cursor returnCursor =   act.getContentResolver().query(contentUri, null, null, null, null);

        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        //int sizeIndex = returnCursor.getColumnIndex(OpenableColumns.SIZE);
        returnCursor.moveToFirst();
        String s_org_file_name = returnCursor.getString(nameIndex);
        String fileExtension = s_org_file_name.substring(s_org_file_name.toString().lastIndexOf("."));
        String fileName = "temporary_file" + (fileExtension != null ? fileExtension : "");
        File tempFile = null;

        if(fileExtension.toLowerCase().compareTo(".rom")!=0){
            return tempFile;
        }
        try {
            tempFile = new File(context.getCacheDir(), fileName);
            if(tempFile.exists()){
                tempFile.delete();
            }
            tempFile.createNewFile();

            FileOutputStream oStream = new FileOutputStream(tempFile);
            InputStream inputStream = context.getContentResolver().openInputStream(contentUri);

            if (inputStream != null) {
                _copy(inputStream, oStream);
            }

            oStream.flush();
            oStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return tempFile;
    }

    private static void _copy(InputStream source, OutputStream target) throws IOException {
        byte[] buf = new byte[8192];
        int length;
        while ((length = source.read(buf)) > 0) {
            target.write(buf, 0, length);
        }
    }
}

interface FramePage{
    int PageNone = -1;
    int PageDevice = 0;
    int PageCommon = 1;
    int PageGlobal = 2;
    int PageTrack1 = 3;
    int PageTrack2 = 4;
    int PageTrack3 = 5;
    int PageiButtonTag = 6;
    int PageiButtonRemove = 7;
    int PageiButtonRemoveTag = 8;
    int PageTotal = PageiButtonRemoveTag+1;
}
