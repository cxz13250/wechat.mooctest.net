package com.mooctest.weixin.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.ansj.util.FilterModifWord;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.mooctest.weixin.dao.QuizAnswerDao;
import com.mooctest.weixin.model.QuizAnswer;
import com.mooctest.weixin.util.AnswerValue;


//聚类功能
public class ClusteringManager {   
    static ArrayList<String> stopwords = new ArrayList<String>();
    static{
		try {
			String filePath = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "stopwords.txt";
			System.out.println(filePath);
			String encoding = "GBK";
			File file = new File(filePath);
			if (file.isFile() && file.exists()) { // 判断文件是否存在
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 编码格式
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					stopwords.add(lineTxt);
				}
				read.close();
			} else {
				System.out.println("找不到指定的文件");
			}
		} catch (Exception e) {
			System.out.println("读取文件内容出错");
			e.printStackTrace();
		}		
		FilterModifWord.insertStopNatures("w", "e", "y", "o", "x", "u", "c", "p", "q", "m", "vshi", "vyou", "h", "k",
                "uj");
        FilterModifWord.insertStopWords(stopwords);
    }
    public static HashMap<String, ArrayList<String>> classify(ArrayList<String> answers) {
    	double numeritalAnswerCount = 0;
    	for (String answer : answers) {
    		if (answer == null || answer.isEmpty()) {
				continue;
			}else if (AnswerValue.containsNumericalValue(answer)) {
				numeritalAnswerCount += 1.0;
			}
		}
    	boolean containsNumericalValue = (numeritalAnswerCount/answers.size()) > 0.5;
    	if (containsNumericalValue) {
    		HashMap<AnswerValue, Integer> map = new HashMap<AnswerValue, Integer>();
    		for (String answer : answers) {
                if (answer == null || answer.isEmpty()) {
                    continue;
                }
                AnswerValue answerValue = new AnswerValue(answer);
                boolean isNew = true;
                for (AnswerValue av : map.keySet()) {
					if (av.equals(answerValue)) {
						isNew = false;
						map.put(av, map.get(av) + 1);
						break;
					}
				}
                if (isNew) {
					map.put(answerValue, 1);
				}
    		}
    		Entry[] entries = getSortedHashtableByValue(map);
    		ArrayList<AnswerValue> keys = new ArrayList<AnswerValue>();
    		int length = answers.size();
            int min = (int) (length * 0.05);
            int max = (int) (length * 0.95);           
            if (entries.length < 2) {
                return null;
            } else {
                for (Entry entry : entries) {
                    int value = (Integer) entry.getValue();
                    AnswerValue key = (AnswerValue) entry.getKey();
                    if (value > min) {
                        keys.add(key);
                    }
                }
            }      
            HashMap<AnswerValue, ArrayList<String>> temp = new HashMap<AnswerValue, ArrayList<String>>();
            HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
            if (keys.size() >= 1) {
                for (AnswerValue key : keys) {
                	temp.put(key, new ArrayList<String>());
                }
                for (String answer : answers) {
                    if (answer == null || answer.isEmpty()) {
                        continue;
                    }
                    boolean isNotClustered = true;
                    for (AnswerValue key : keys) {
						if (new AnswerValue(answer).equals(key)){
							temp.get(key).add(answer);
							isNotClustered = false;
							break;
						}
					}
                    if (isNotClustered) {
						if (result.containsKey("")) {
							result.get("").add(answer);
						}else {
							result.put("", new ArrayList<String>());
							result.get("").add(answer);
						}
					}
                }
            }else {
                return null;
            }
            for (AnswerValue answerValue : temp.keySet()) {
				result.put(answerValue.toString(), temp.get(answerValue));
				System.out.println(Arrays.toString(temp.get(answerValue).toArray()));
			}      
            return result;    		
		}	
        TreeMap<String, Integer> map = new TreeMap<String, Integer>();        
        for (String answer : answers) {
            if (answer == null || answer.isEmpty()) {
                continue;
            }
            List<Term> list = ToAnalysis.parse(answer);
            List<Term> modifResult = FilterModifWord.modifResult(list);
            for (Term term : modifResult) {
                String word = term.getName();
                if (map.containsKey(word)) {
                    int count = map.get(word);
                    map.put(word, count + 1);
                } else {
                    map.put(word, 1);
                }
            }
        }
        int length = answers.size();
        int min = (int) (length * 0.05);
        int max = (int) (length * 0.95);
        Entry[] entries = getSortedHashtableByValue(map);
        ArrayList<String> keys = new ArrayList<String>();
        if (entries.length < 2) {
            return null;
        } else {
            for (Entry entry : entries) {
                int value = (Integer) entry.getValue();
                String key = (String) entry.getKey();
                if (value > min && value < max) {
                    keys.add(key);
                }
            }
        }
        HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
        if (keys.size() >= 2) {
            for (String key : keys) {
                result.put(key, new ArrayList<String>());
            }          
            for (String answer : answers) {
                if (answer == null || answer.isEmpty()) {
                    continue;
                }
                boolean isNotClustered = true;
                for (int i = keys.size() - 1; i >= 0; i--) {
					String key = keys.get(i);
					if (answer.contains(key)) {
						result.get(key).add(answer);
						isNotClustered = false;
						break;
					}
				}
                if (isNotClustered) {
                	if (result.containsKey("")) {
						result.get("").add(answer);
					}else {
						result.put("", new ArrayList<String>());
						result.get("").add(answer);
					}
				}
            }
        }else {
            return null;
        }       
        Object[] array = result.keySet().toArray();       
        for (Object key : array) {
        	if (result.get((String)key).size() == 0) {
				result.remove((String)key);
			}else if (result.get((String)key).size() == 1) {
        		if (result.containsKey("")) {
					result.get("").add(result.get((String)key).get(0));
				}else {
					result.put("", new ArrayList<String>());
					result.get("").add(result.get((String)key).get(0));
				}
        		result.remove((String)key);
			}
        }
        return result;
    }  
    public static HashMap<String, ArrayList<String>> classify(String[] answers) {
        TreeMap<String, Integer> map = new TreeMap<String, Integer>();
        FilterModifWord.insertStopNatures("w", "e", "y", "o", "x", "u", "c", "p", "q", "m", "vshi", "vyou", "h", "k",
                "uj");
        for (String answer : answers) {
            List<Term> list = ToAnalysis.parse(answer);
            List<Term> modifResult = FilterModifWord.modifResult(list);
            for (Term term : modifResult) {
                String word = term.getName();
                if (map.containsKey(word)) {
                    int count = map.get(word);
                    map.put(word, count + 1);
                } else {
                    map.put(word, 1);
                }
            }
        }
        int length = answers.length;
        int min = (int) (length * 0.15);
        int max = (int) (length * 0.95);
        Entry[] entries = getSortedHashtableByValue(map);
        ArrayList<String> keys = new ArrayList<String>();
        if (entries.length < 2) {
            return null;
        } else {
            for (Entry entry : entries) {
                if (keys.size() == 2) {
                    break;
                }
                int value = (Integer) entry.getValue();
                String key = (String) entry.getKey();
                if (value > min && value < max) {
                    keys.add(key);
                }
            }
        }
        HashMap<String, ArrayList<String>> result = new HashMap<String, ArrayList<String>>();
        if (keys.size() == 2) {
            for (String key : keys) {
                result.put(key, new ArrayList<String>());
            }
            result.put("", new ArrayList<String>());
            for (String answer : answers) {
                if (answer.contains(keys.get(1))) {
                    result.get(keys.get(1)).add(answer);
                } else if (answer.contains(keys.get(0))) {
                    result.get(keys.get(0)).add(answer);
                } else {
                    result.get("").add(answer);
                }
            }
        }else {
            return null;
        }
        return result;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static Map.Entry[] getSortedHashtableByValue(Map h) {
        Set set = h.entrySet();
        Map.Entry[] entries = (Map.Entry[]) set.toArray(new Map.Entry[set.size()]);
        Arrays.sort(entries, new Comparator() {
            public int compare(Object arg0, Object arg1) {
                Long key1 = Long.valueOf(((Map.Entry) arg0).getValue().toString());
                Long key2 = Long.valueOf(((Map.Entry) arg1).getValue().toString());
                return key2.compareTo(key1);
            }
        });
        return entries;
    }
}

