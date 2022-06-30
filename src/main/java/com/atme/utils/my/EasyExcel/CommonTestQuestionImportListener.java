package com.atme.utils.my.EasyExcel;

/**
 * @author amao
 * @create 2022-06-25-11:04
 */

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;
import com.alibaba.excel.metadata.data.ReadCellData;


import java.util.Map;

/**
 * 通用测试试题导入监听器.
 *
 * @author simon
 * @version 1.0 2021/12/30
 * @since 1.0
 */
@Slf4j
public class CommonTestQuestionImportListener extends AnalysisEventListener<Map<Integer, String>> {

    // **********************
    // EasyExcel.read(file.getInputStream(), new CommonTestQuestionImportListener(id, questionRepository, optionRepository)).sheet().doRead();
    // **********************



//    private CommonTestQuestionRepository questionRepository;
//    private CommonTestOptionRepository optionRepository;
//    private Long paperId;

//    public CommonTestQuestionImportListener(Long paperId, CommonTestQuestionRepository questionRepository, CommonTestOptionRepository optionRepository) {
//        this.paperId = paperId;
//        this.questionRepository = questionRepository;
//        this.optionRepository = optionRepository;
//    }



    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        super.invokeHead(headMap, context);
    }

    @Override
    public void invoke(Map<Integer, String> map, AnalysisContext analysisContext) {
//        log.info(JsonUtils.getJsonFromObject(map));
//        if (null == map.get(0)) {
//            return;
//        }
//        CommonTestQuestion question = new CommonTestQuestion();
//        question.setPaperId(paperId);
//        question.setType(Integer.valueOf(map.get(0)));
//        question.setContent(map.get(1));
//        question.setScore(Double.valueOf(map.get(2)));
//        questionRepository.save(question);
//
//        List<CommonTestOption> optionList = new ArrayList<>();
//        for (int i = 3; i < Integer.MAX_VALUE; i = i + 2) {
//            if (StringUtils.isBlank(map.get(i))) {
//                break;
//            }
//            CommonTestOption option = new CommonTestOption();
//            option.setQuestionId(question.getId());
//            option.setContent(map.get(i));
//            option.setIsCorrect(Integer.valueOf(map.get(i + 1)));
//            optionList.add(option);
//        }
//
//        optionList.forEach(e -> {
//            optionRepository.save(e);
//        });
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
    }

    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
    }
}
