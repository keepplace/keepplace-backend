/**
 @file YoutubeDlParser.java
 @brief youtube-dl Json 輸出分析程式

 本來源程式碼為「youtube-dl-helper」軟體的一部份
 This source code is part of "youtube-dl-helper" software
 https://github.com/NTOUCS-Java-Programming-103-Team-13/youtube-dl-helper

 本來源程式碼的架構基於「通用程式來源程式碼範本」專案
 This source code's structure is based on "Generic Program Source Code Templates" project
 https://github.com/Vdragon/Generic_Program_Source_Code_Templates

 建議的文字編輯器設定
 Recommended text editor settings
 Indentation by tab character
 Tab character width = 2 space characters

 @author 林博仁(09957010) <Henry.Lin.Taiwan@gmail.com>
 @author 林夏媛 <dorislin8737@gmail.com>
 @copyright The software has been released into public domain.
 */

/* Imported Java packages */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import org.json.*;

/**
 * @brief YoutubeDlParser 類別
 */
public class YoutubeDLParser {


    public static HashMap<String, String> getMediaSupportedFormats(String url) {
        HashMap<String, String> formats = new HashMap<String, String>();
        Process youtube_dl_process;
        try {
//            youtube_dl_process = Runtime.getRuntime().exec("youtube-dl --dump-json " + url);
            youtube_dl_process = Runtime.getRuntime().exec("youtube-dl " +
                    "--print-json " +
//                    "--verbose " +
//                    "--no-playlist " +
//                    "-o '/tmp/%(uploader)s/%(title)s-%(id)s.%(ext)s'" +
                    url);

            String line = null;
            String json_data = new String();
            InputStream youtube_dl_process_standard_output = youtube_dl_process.getInputStream();
            BufferedReader youtube_dl_output_reader = new BufferedReader(new InputStreamReader(youtube_dl_process_standard_output));
            while ((line = youtube_dl_output_reader.readLine()) != null) {
                json_data += line;
            }
            youtube_dl_output_reader.close();


            JSONObject media_json = new JSONObject(json_data);
            System.out.println(media_json.toString(4));


//            JSONArray format_json_array = media_json.getJSONArray("formats");
//            for (int array_index = 0; array_index < format_json_array.length(); ++array_index) {
//                formats.put(
//                        format_json_array.getJSONObject(array_index).getString("format_id"),
//                        format_json_array.getJSONObject(array_index).getString("format")
//                );
//            }

            youtube_dl_process.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return formats;
    }

    public static ArrayList<String> getMediaSupportedSubtitles(String media_url) {
        ArrayList<String> subtitles = new ArrayList<String>();
        Process youtube_dl_process;
        try {
            youtube_dl_process = Runtime.getRuntime().exec("youtube-dl --all-subs --dump-json " + media_url);

            String line = null;
            String json_data = new String();
            InputStream youtube_dl_process_standard_output = youtube_dl_process.getInputStream();
            BufferedReader youtube_dl_output_reader = new BufferedReader(new InputStreamReader(youtube_dl_process_standard_output));
            while ((line = youtube_dl_output_reader.readLine()) != null) {
                json_data += line;
            }
            youtube_dl_output_reader.close();

            JSONObject media_json = new JSONObject(json_data);

            if (media_json.get("subtitles") != null) {
                JSONObject subtitles_object = media_json.getJSONObject("subtitles");
                for (Iterator<String> i = subtitles_object.keys(); i.hasNext(); ) {
                    subtitles.add(i.next());
                }
            }

            youtube_dl_process.waitFor();
            media_json = null;

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return subtitles;

    }

    public static void main(String[] args) {
        System.out.println("－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－－");
//        HashMap<String, String> mediaSupportedFormats = getMediaSupportedFormats("https://www.youtube.com/watch?v=cqgnLhiccGw");
        HashMap<String, String> mediaSupportedFormats = getMediaSupportedFormats("https://www.youtube.com/watch?v=e04d8ILXlKw");
        System.out.println(mediaSupportedFormats.toString());
        return;
    }
}

