package com.plightpad.data;

import com.richpath.RichPath;
import com.richpath.RichPathView;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Szczypiorek on 16.09.2017.
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResultPaths {

    RichPath outline;
    RichPath inlineCrossed;
    RichPath inlineHorizontal;
    RichPath filledDownTriangle;

    public ResultPaths(RichPathView richPathView) {
        outline = richPathView.findRichPathByName(XMLPathNames.OUTLINE.getXmlId());
        inlineCrossed = richPathView.findRichPathByName(XMLPathNames.INLINE_CROSSED.getXmlId());
        inlineHorizontal = richPathView.findRichPathByName(XMLPathNames.INLINE_HORIZONTAL.getXmlId());
        filledDownTriangle = richPathView.findRichPathByName(XMLPathNames.FILLED_DOWN_TRIANGLE.getXmlId());
    }

    @Getter
    @AllArgsConstructor
    private enum XMLPathNames {
        OUTLINE("outline"),
        INLINE_CROSSED("inline_cross"),
        INLINE_HORIZONTAL("inline_horizontal"),
        FILLED_DOWN_TRIANGLE("filling_down_triangle");

        private String xmlId;
    }

}
