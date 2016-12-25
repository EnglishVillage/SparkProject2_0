package MyWork;

import java.io.*;
import java.util.*;

/**
 * Created by Administrator on 2016/8/22.
 */
public class ColorTest {

    public static void main(String[] args) throws Exception {
        //test("d:/a.txt","D:/aaaaa.txt");
        //test("d:/a.txt", "D:/aa.txt");
        //sum("d:/Unhandled", "d:/Unhandled/ColorSumNew.txt");


        //compare("d:/ColorSum.txt","d:/ColorSumNew.txt","d:/ColorAdd.txt");

        //sumsort("d:/Color", "d:/Color/ColorSumTotalSe.txt");

        //jianse("D:/ColorTotal/SeTotal.txt", "D:/ColorTotal/ColorNo.txt");
        //filter("D:/ColorTotal/ColorNo.txt");
        //sumsort("d:/ColorTotal/ColorNo.txt.col.txt","d:/ColorTotal/ColorNo.txt.colno.txt","d:/ColorTotal/ColorNoAdd.txt","d:/ColorTotal/NoTotal.txt");


//        group("D:/ColorTotal/SeTotal.txt", "d:/LastColor");
//        group("d:/ColorTotal/NoTotal.txt", "d:/LastColorNo");
        //split("D:/ColorTotal/SeTotal.txt.no.txt");
        //okSum("d:/ColorTotal/ok","d:/ColorTotal/ok/SeTotal.txt");

        //transferKeysValue("d:/ColorTotal/SeTotal.txt", "d:/ColorTotal/SeKeysValue.txt");
        //createRulesOld("d:/ColorTotal/SeKeysValue.txt","d:/ColorTotal/SeETL.txt");
        success("d:/ColorTotal/SeTotal.txt", "d:/Work/ColorNoAdd.txt");
    }

    static void success(String filePath, String colorNoAddFilePath) throws Exception {
        jianse(filePath, filePath + ".ColorNo.txt");
        filter(filePath + ".ColorNo.txt", filePath + ".MatchColor.txt", filePath + ".MismatchColor.txt");
        sumsort(filePath + ".MatchColor.txt", filePath + ".MismatchColor.txt", colorNoAddFilePath, filePath + ".NoTotal.txt");

        transferKeysValue(filePath + ".NoTotal.txt", filePath + ".NoSeKeysValue.txt");
        transferKeysValue(filePath, filePath + ".SeKeysValue.txt");

        createRulesOld(filePath + ".ETL.txt", filePath + ".SeKeysValue.txt", filePath + ".NoSeKeysValue.txt");

        String[] fileArr = {filePath + ".ColorNo.txt", filePath + ".MatchColor.txt", filePath + ".MismatchColor.txt", filePath + ".SeKeysValue.txt", filePath + ".NoSeKeysValue.txt"};
        for (String file : fileArr) {
            new File(file).delete();
        }
    }

    /**
     * 从网页的文本中提取颜色出来,需要DIY
     */
    static void test(String readFile, String writeFile) throws Exception {
        BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(readFile)));
        BufferedWriter bufw = new BufferedWriter(new FileWriter(writeFile));
        String line = null;
        int num = 0;
        while ((line = bufr.readLine()) != null) {
            num++;
            String[] arr = line.split("：");
            if (arr.length > 0) {
                bufw.write(arr[0].trim() + "\r\n");
            }
        }

        bufr.close();
        bufw.flush();
        bufw.close();
    }

    /**
     * 将文件转化为规则
     */
    static void createRulesOld(String disFile, String... souFiles) throws Exception {
        StringBuilder sbTotal = new StringBuilder();
        sbTotal.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
        sbTotal.append("<etl>\r\n");

        StringBuilder sb = null;
        sb = new StringBuilder();
        sb.append("<field>\r\n");
        sb.append("<key>color</key>\r\n");
        sb.append("<actions>\r\n");
        sb.append("</actions>\r\n");
        sb.append("<values>\r\n");
        BufferedReader bufr;
        String line = null;
        for (String souFile : souFiles) {
            bufr = new BufferedReader(new InputStreamReader(new FileInputStream(souFile)));
            while ((line = bufr.readLine()) != null) {
                line = line.trim();
                if (line.length() > 0) {
                    sb.append(line + "\r\n");
                }
            }
            bufr.close();
        }
        sb.append("</values>\r\n");
        sb.append("</field>\r\n");
        sbTotal.append(sb);
        sbTotal.append("</etl>\r\n");

        BufferedWriter bufw = new BufferedWriter(new FileWriter(disFile));
        bufw.write(sbTotal.toString());
        bufw.flush();
        bufw.close();
    }

    /**
     * 将文件转化为规则
     */
    static void createRulesNew(String disFile, String... dirPaths) throws Exception {
        StringBuilder sbTotal = new StringBuilder();
        sbTotal.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
        sbTotal.append("<etl>\r\n");
        for (String dirPath : dirPaths) {
            File dir = new File(dirPath);
            File[] files = dir.listFiles();
            StringBuilder sb = null;
            for (File file : files) {
                sb = new StringBuilder();
                sb.append("<field>\r\n");
                sb.append("<key>" + file.toString() + "</key>\r\n");
                sb.append("<actions>\r\n");
                sb.append("</actions>\r\n");
                sb.append("<values>\r\n");
                BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
                String line = null;
                while ((line = bufr.readLine()) != null) {
                    line = line.trim();
                    if (line.length() > 0) {
                        sb.append(line + "\r\n");
                    }
                }
                bufr.close();
                sb.append("</values>\r\n");
                sb.append("</field>\r\n");
            }
            sbTotal.append(sb);
        }
        sbTotal.append("</etl>\r\n");


        BufferedWriter bufw = new BufferedWriter(new FileWriter(disFile));
        bufw.write(sbTotal.toString());
        bufw.flush();
        bufw.close();
    }


    /**
     * 将单个键值对转化为多个键一个值的形式
     */
    static void transferKeysValue(String readFile, String writeFile) throws Exception {
        HashSet<String> keyHS = new HashSet<String>();
        ArrayList<String> totalList = new ArrayList<String>();
        BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(readFile)));
        String line = null;
        while ((line = bufr.readLine()) != null) {
            line = line.trim();
            if (line.length() > 0) {
                totalList.add(line);
                keyHS.add(line.substring(line.indexOf(",") + 1));
            }
        }
        bufr.close();

        HashMap<String, StringBuilder> sumHM = new HashMap<String, StringBuilder>();
        for (String key : keyHS) {
            sumHM.put(key, new StringBuilder());
        }
        int index;
        String key;
        String value;
        for (String str : totalList) {
            index = str.indexOf(",");
            key = str.substring(index + 1);
            value = str.substring(0, index);
            StringBuilder sb = (StringBuilder) sumHM.get(key);
            sb.append(value + ",");
        }

        BufferedWriter bufw = new BufferedWriter(new FileWriter(writeFile));
        for (String keyStr : keyHS) {
            StringBuilder sb = (StringBuilder) sumHM.get(keyStr);
            bufw.write("<value altvalues=\"" + sb.substring(0, sb.length() - 1) + "\">" + keyStr + "</value>\r\n");
        }
        bufw.flush();
        bufw.close();
    }

    /**
     * 将键值对的文件合并成一个文件,并按键长度进行降序
     */
    static void okSum(String dirPath, String disFile) throws Exception {
        HashSet<String> hs = new HashSet<String>();
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        for (File file : files) {
            BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = null;
            while ((line = bufr.readLine()) != null) {
                line = line.trim();
                if (line.length() > 0) {
                    hs.add(line);
                }
            }
            bufr.close();
        }

//        ArrayList<String> list = new ArrayList<>(hs);
//        list.sort(new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                //根据长度降序
//                return o2.indexOf(",") - o1.indexOf(",");
//            }
//        });
        ArrayList<String> list = sortByKeyLength(hs);

        BufferedWriter bufw = new BufferedWriter(new FileWriter(disFile));
        for (String str : list) {
            bufw.write(str + "\r\n");
        }
        bufw.flush();
        bufw.close();
    }

    /**
     * 将有值的色和没有值的色分割成2个文件
     */
    static void split(String souFile) throws Exception {
        HashSet<String> okHS = new HashSet<>();
        HashSet<String> noHS = new HashSet<>();
        BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(souFile)));
        String line = null;
        while ((line = bufr.readLine()) != null) {
            line = line.trim();
            if (line.length() > 0) {
                if (line.contains(",")) {
                    okHS.add(line);
                } else {
                    noHS.add(line);
                }
            }
        }

//        ArrayList<String> oklist = new ArrayList<>(okHS);
//        oklist.sort(new Comparator<String>() {
//            @Override
//            public int compare(String o1, String o2) {
//                //根据长度降序
//                return o2.indexOf(",") - o1.indexOf(",");
//            }
//        });
        ArrayList<String> oklist = sortByKeyLength(okHS);

        ArrayList<String> nolist = new ArrayList<>(noHS);
        nolist.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                //根据长度降序
                return o2.length() - o1.length();
            }
        });


        BufferedWriter bufw = new BufferedWriter(new FileWriter(souFile + ".ok.txt"));
        for (String str : oklist) {
            bufw.write(str + "\r\n");
        }
        bufw.flush();
        bufw.close();
        bufw = new BufferedWriter(new FileWriter(souFile + ".no.txt"));
        for (String str : nolist) {
            bufw.write(str + "\r\n");
        }
        bufw.flush();
        bufw.close();
    }

    /**
     * 合并加排序
     */
    static void sumsort(String dirPath, String disFile) throws Exception {
        HashSet<String> hs = new HashSet<String>();
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        for (File file : files) {
            BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = null;
            while ((line = bufr.readLine()) != null) {
                line = line.trim();
                if (line.length() > 0) {
                    hs.add(line);
                }
            }
            bufr.close();
        }

        ArrayList<String> list = new ArrayList<>(hs);
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                //根据长度降序
                return o2.length() - o1.length();
            }
        });

        BufferedWriter bufw = new BufferedWriter(new FileWriter(disFile));
        for (String str : list) {
            bufw.write(str + "\r\n");
        }
        bufw.flush();
        bufw.close();
    }

    /**
     * 合并加排序
     */
    static void sumsort(String disFile, String... souFiles) throws Exception {
        HashSet<String> hs = new HashSet<String>();
        for (String file : souFiles) {
            BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = null;
            while ((line = bufr.readLine()) != null) {
                line = line.trim();
                if (line.length() > 0) {
                    hs.add(line);
                }
            }
            bufr.close();
        }

        ArrayList<String> list = new ArrayList<>(hs);
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                //根据长度降序
                return o2.length() - o1.length();
            }
        });

        BufferedWriter bufw = new BufferedWriter(new FileWriter(disFile));
        for (String str : list) {
            bufw.write(str + "\r\n");
        }
        bufw.flush();
        bufw.close();
    }

    static void sumSortByKeyLength(String disFile, String... souFiles) throws Exception {
        HashSet<String> hs = new HashSet<String>();
        for (String file : souFiles) {
            BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = null;
            while ((line = bufr.readLine()) != null) {
                line = line.trim();
                if (line.length() > 0) {
                    hs.add(line);
                }
            }
            bufr.close();
        }

        ArrayList<String> list = sortByKeyLength(hs);

        BufferedWriter bufw = new BufferedWriter(new FileWriter(disFile));
        for (String str : list) {
            bufw.write(str + "\r\n");
        }
        bufw.flush();
        bufw.close();
    }

    /**
     * "aaa,bbb"的形式,按键进行倒序
     */
    static ArrayList<String> sortByKeyLength(Collection<String> coll) {
        ArrayList<String> list = new ArrayList<>(coll);
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                //根据长度降序
                return o2.indexOf(",") - o1.indexOf(",");
            }
        });
        return list;
    }

    /**
     * 比较旧新2个文件,并生成新增加的文件
     */
    static void compare(String oldFile, String newFile, String addFile) throws Exception {
        String line = null;
        HashSet<String> oldList = new HashSet<String>();
        HashSet<String> newList = new HashSet<String>();
        HashSet<String> addList = new HashSet<String>();

        BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(oldFile)));
        while ((line = bufr.readLine()) != null) {
            oldList.add(line);
        }
        bufr.close();
        bufr = new BufferedReader(new InputStreamReader(new FileInputStream(newFile)));
        while ((line = bufr.readLine()) != null) {
            newList.add(line);
        }
        bufr.close();

        boolean flag;
        for (String newS : newList) {
            flag = false;
            for (String old : oldList) {
                if (old.equals(newS)) {
                    flag = true;
                    break;
                }
            }
            if (!flag) {
                addList.add(newS);
            }
        }

        BufferedWriter bufw = new BufferedWriter(new FileWriter(addFile));
        for (String str : addList) {
            bufw.write(str + "\r\n");
        }
        bufw.flush();
        bufw.close();
    }

    /**
     * 将没有色的文件中,进行分类,分为2类
     */
    static void filter(String filePath, String matchFile, String mismatchFile) throws Exception {
        HashSet<String> colHS = new HashSet<>();
        HashSet<String> colNoHS = new HashSet<>();
        String[] colorArr = {"黑", "黒", "红", "粉", "灰", "蓝", "白", "棕", "紫", "黄", "绿", "緑", "橙", "褐", "青", "赤", "靛", "绀", "丹", "朱", "茜", "苍", "墨", "绯", "卡其", "其它", "其他"};

        BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        String line = null;
        String color = null;
        Boolean isSe;
        while ((line = bufr.readLine()) != null) {
            isSe = false;
            color = line.substring(0, line.indexOf(","));
            for (String str : colorArr) {
                if (color.contains(str)) {
                    isSe = true;
                    break;
                }
            }
            if (isSe) {
                colHS.add(line);
            } else {
                colNoHS.add(line);
            }
        }

        //BufferedWriter bufw = new BufferedWriter(new FileWriter(filePath + ".col.txt"));
        BufferedWriter bufw = new BufferedWriter(new FileWriter(matchFile));
        for (String str : colHS) {
            bufw.write(str + "\r\n");
        }
        bufw.flush();
        bufw.close();

        //bufw = new BufferedWriter(new FileWriter(filePath + ".colno.txt"));
        bufw = new BufferedWriter(new FileWriter(mismatchFile));
        for (String str : colNoHS) {
            bufw.write(str + "\r\n");
        }
        bufw.flush();
        bufw.close();
    }

    /**
     * 按长度生成不同长度的颜色文件
     */
    static void group(String filePath, String disDir) throws Exception {
        HashSet<String> hs = new HashSet<>();
        BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
        String line = null;
        while ((line = bufr.readLine()) != null) {
            if (line != null && line.length() > 0) {
                hs.add(line);
            }
        }
        bufr.close();

        ArrayList<String> list = new ArrayList<>(hs);
        list.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                //根据长度降序
                return o2.length() - o1.length();
            }
        });

        File dir = new File(disDir);
        if (!dir.exists() || !dir.isDirectory()) {
            dir.mkdirs();
        }

        int length = 0;
        BufferedWriter bufw = null;
        String chiFileName = "/color";
        for (String str : list) {
            if (str.length() != length) {
                length = str.length();
                if (bufw != null) {
                    bufw.flush();
                    bufw.close();
                }
                bufw = new BufferedWriter(new FileWriter(disDir + chiFileName + length + ".txt"));
            }
            bufw.write(str + "\r\n");
        }

        bufw.flush();
        bufw.close();
    }

    /**
     * 合并加排序
     */
    static void sum(String dirPath, String disFile) throws Exception {
        HashSet<String> colHS = new HashSet<String>();
        HashSet<String> colNoHS = new HashSet<String>();
        File dir = new File(dirPath);
        File[] files = dir.listFiles();
        for (File file : files) {
            BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = null;
            while ((line = bufr.readLine()) != null) {
                line = line.trim();
                if (line.length() > 0) {
                    if (line.contains("色")) {
                        colHS.add(line);
                    } else {
                        colNoHS.add(line);
                        colHS.add(line + "色");
                    }
                }
            }
            bufr.close();
        }

        ArrayList<String> colList = new ArrayList<>(colHS);
        colList.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                //根据长度降序
                return o2.length() - o1.length();
            }
        });
        ArrayList<String> colNoList = new ArrayList<>(colNoHS);
        colNoList.sort(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                //根据长度降序
                return o2.length() - o1.length();
            }
        });

        BufferedWriter bufw = new BufferedWriter(new FileWriter(disFile));
        for (String str : colList) {
            bufw.write(str + "\r\n");
        }
        bufw.flush();
        bufw.close();

        bufw = new BufferedWriter(new FileWriter(disFile + "nocol.txt"));
        for (String str : colNoList) {
            bufw.write(str + "\r\n");
        }
        bufw.flush();
        bufw.close();
    }

    /**
     * 给每行没有色字的颜色加上色字.
     */
    static void jiase(String readFile, String writeFile) throws Exception {
        ArrayList<String> seList = new ArrayList<>();
        ArrayList<String> noList = new ArrayList<>();

        BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(readFile)));
        String line = null;
        while ((line = bufr.readLine()) != null) {
            if (line.contains("色")) {
                seList.add(line);
            } else {
                noList.add(line);
            }
        }

        BufferedWriter bufw = new BufferedWriter(new FileWriter(writeFile));
        for (String str : seList) {
            bufw.write(str + "\r\n");
        }
        //bufw.write("======================================================================");
        for (String str : noList) {
            bufw.write(str + "色" + "\r\n");
            //bufw.write(str+"\r\n");
        }
        //bufw.write("======================================================================");


        bufr.close();
        bufw.flush();
        bufw.close();
    }

    /**
     * 给每行有色字的颜色减去色字.
     */
    static void jianse(String readFile, String writeFile) throws Exception {
        HashSet<String> noHS = new HashSet<>();

        BufferedReader bufr = new BufferedReader(new InputStreamReader(new FileInputStream(readFile)));
        String line = null;
        String color = null;
        while ((line = bufr.readLine()) != null) {
            color = line.substring(0, line.indexOf(","));
            if (color.length() > 0) {
                if (color.contains("色") && color.charAt(color.length() - 1) == '色') {
                    noHS.add(color.substring(0, color.length() - 1) + line.substring(line.indexOf(",")));
                } else {
                    System.out.println(line);
                }
            }
        }

        BufferedWriter bufw = new BufferedWriter(new FileWriter(writeFile));
        for (String str : noHS) {
            bufw.write(str + "\r\n");
        }

        bufr.close();
        bufw.flush();
        bufw.close();
    }

    /**
     * 合并加排序
     */
    static void sumsort(String oldFile, String newFile, String addFile, String totalFile) throws Exception {
        String tmp = "d:/addddd.txt";
        compare(oldFile, newFile, tmp);
        sumSortByKeyLength(totalFile, oldFile, tmp);
        File file = new File(tmp);
        file.delete();
    }
}
