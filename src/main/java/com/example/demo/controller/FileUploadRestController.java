package com.example.demo.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api")
public class FileUploadRestController {

	@PostMapping("upload")
    public Object post(
            @RequestParam("upload_file") MultipartFile multipartFile,
            @RequestParam("file_name") String fileName  // ファイル種類
            ){
		System.out.println("=====================================================");
		System.out.println(multipartFile.getName());
		System.out.println(String.valueOf(multipartFile.getSize()));
        // ファイルが空の場合は異常終了
        if(multipartFile.isEmpty()){
            // 異常終了時の処理
        	return "error";
        }
        System.out.println(fileName);
//        String[] name = fileType.split(Pattern.quote("."));
//        System.out.println(name[0]);

        // ファイル種類から決まる値をセットする
        StringBuffer filePath = new StringBuffer("uploadfile")
                                        .append(File.separator);   //ファイルパス
        System.out.println(filePath);

        // アップロードファイルを格納するディレクトリを作成する
        File uploadDir = mkdirs(filePath);

        System.out.println(uploadDir.toString());

        try {
            // アップロードファイルを置く
            FileOutputStream uploadFile =
                    new FileOutputStream(uploadDir.getPath() + "/" + fileName + ".jpg");
            byte[] bytes = multipartFile.getBytes();
            BufferedOutputStream uploadFileStream =
                    new BufferedOutputStream(uploadFile);
            uploadFileStream.write(bytes);
            uploadFileStream.close();

            return "You successfully uploaded.";
        } catch (Exception e) {
            // 異常終了時の処理
        	return e.getMessage();
        } catch (Throwable t) {
            // 異常終了時の処理
        	return t.getMessage();
        }
    }

    /**
     * アップロードファイルを格納するディレクトリを作成する
     *
     * @param filePath
     * @return
     */
    private File mkdirs(StringBuffer filePath){
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        File uploadDir = new File(filePath.toString(), sdf.format(now));
        // 既に存在する場合はプレフィックスをつける
        int prefix = 0;
        while(uploadDir.exists()){
            prefix++;
            uploadDir =
                    new File(filePath.toString() + sdf.format(now) + "-" + String.valueOf(prefix));
        }

        // フォルダ作成
        uploadDir.mkdirs();

        return uploadDir;
    }

}
