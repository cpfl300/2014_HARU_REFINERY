package refinery.utils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TestUtils {
	
	private static final Logger log = LoggerFactory.getLogger(TestUtils.class);
	
	public static List<String> getFieldNames(Field[] fields) {
		List<String> fieldNames = new ArrayList<String>();
		
		for (Field field : fields) {
			fieldNames.add(field.getName());
		}
		
		return fieldNames;
		
	}
	
	public static <T> void initComplementaryFields(List<T> objs, String[] fieldNameArr) {
		
		// list 사이즈 검증
		if (objs.size() == 0) {
			throw new IllegalArgumentException("list size too short to clean up object");
		}
		
		T obj = objs.get(0);
		if (obj == null) {
			throw new IllegalArgumentException("first object is null");
		}
		
		Class<? extends Object> clazz = obj.getClass();
		Field[] fieldsInObj = clazz.getDeclaredFields();
		
		// class에 필드가 존재하지 않는 경우
		if (fieldsInObj.length <= 1) {
			// this field 포함됨
			throw new FieldNotFoundException(clazz.getName() + " doesn't have any fields");
		}
		
		// target fieldName이 존재하지 않는 경우
		if (fieldNameArr.length == 0) {
			throw new IllegalArgumentException("need target fields for finding");
		}
		
		// complementary set
		List<String> fieldNames = TestUtils.getFieldNames(fieldsInObj);		
		fieldNames.removeAll(Arrays.asList(fieldNameArr));
		
		// 필드 수정
		for (T o : objs) {
			clazz = o.getClass();
			
			for (String fieldName : fieldNames) {
				Field field = null;
				
				try {
					field = clazz.getDeclaredField(fieldName);
					field.setAccessible(true);
					try {
						field.set(o, null);
					} catch (IllegalArgumentException | IllegalAccessException e) {
						log.debug(e.getMessage());
					}
					
				} catch (NoSuchFieldException | SecurityException e) {
					throw new FieldNotFoundException(fieldName + " could not find at " + clazz.getName() + " using TestUtils.checkFields");
					
				}
				
			}
		}
	}
}
