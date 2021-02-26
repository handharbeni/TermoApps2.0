package dev.mhandharbeni.termoapps20.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.mlkit.vision.face.Face;

import java.util.ArrayList;
import java.util.List;

public class FaceBoundOverlay extends View {
    private List<Face> facesBounds = new ArrayList<>();
    private Paint anchorPaint = new Paint();
    private Paint idPaint = new Paint();
    private Paint boundsPaint = new Paint();

    public FaceBoundOverlay(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        anchorPaint.setColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));

        idPaint.setColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));
        idPaint.setTextSize(40f);

        boundsPaint.setStyle(Paint.Style.STROKE);
        boundsPaint.setColor(ContextCompat.getColor(context, android.R.color.holo_blue_dark));
        boundsPaint.setStrokeWidth(4f);
    }

    public void updateFaces(List<Face> facesBounds){
        this.facesBounds.clear();
        this.facesBounds.addAll(facesBounds);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (Face facesBound : facesBounds) {
//            drawAnchor(facesBound.getBoundingBox());
//            drawId();
            drawBounds(facesBound.getBoundingBox());
        }
    }

    private void drawAnchor(PointF center){

    }

    private void drawId(String faceId, PointF center){

    }

    private void drawBounds(Rect box){
        RectF rectFBox = new RectF();
        rectFBox.set(box);

        new Canvas().drawRect(rectFBox, boundsPaint);
    }

    private PointF center(){
        int centerX = getLeft() + (getRight() - getLeft()) / 2;
        int centerY = getTop() + (getBottom() - getTop()) / 2;
        return new PointF(centerX, centerY);
    }
}
