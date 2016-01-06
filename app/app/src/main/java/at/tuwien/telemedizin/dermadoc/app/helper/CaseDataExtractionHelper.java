package at.tuwien.telemedizin.dermadoc.app.helper;

import java.util.LinkedList;
import java.util.List;

import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.AdviceParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.CaseDataParc;
import at.tuwien.telemedizin.dermadoc.app.entities.parcelable.casedata.CaseInfoParc;
import at.tuwien.telemedizin.dermadoc.entities.casedata.CaseData;

/**
 * Created by FAUser on 04.01.2016.
 */
public class CaseDataExtractionHelper<T extends CaseDataParc> {

    final Class<T> typeParameterClass;

    public CaseDataExtractionHelper(Class<T> typeParameterClass) {
        this.typeParameterClass = typeParameterClass;
    }

    public List<T> extractElements(List<CaseDataParc> caseDataList) {
        List<T> extractionResult = new LinkedList<>();

        for (CaseDataParc cd : caseDataList) {
            // check if the object is instance of the generic parameter-class
            if (typeParameterClass.isInstance(cd)) {
                extractionResult.add(typeParameterClass.cast(cd));
            }
        }
        return extractionResult;
    }


    public static CaseInfoParc getLatestCaseInfo(List<CaseDataParc> caseDataList) {
        CaseDataExtractionHelper<CaseInfoParc> caseInfoExtractor = new CaseDataExtractionHelper<>(CaseInfoParc.class);
        // get all caseInfo-objects
        List<CaseInfoParc> caseInfos = caseInfoExtractor.extractElements(caseDataList);
        if (caseInfos.size() == 0) {
            return null;
        }
        CaseInfoParc cI = caseInfos.get(0);
        return cI;
    }
}
