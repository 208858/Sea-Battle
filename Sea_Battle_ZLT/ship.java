package Sea_Battle_ZLT;

import java.util.Objects;

public class ship {
    private final int start_x;
    private final int start_y;
    private final int end_x;
    private final int end_y;
    private final int length;

    public ship(int start_x, int start_y, int end_x, int end_y, int length) {
        this.start_x = start_x;
        this.start_y = start_y;
        this.end_x = end_x;
        this.end_y = end_y;
        this.length = length;
    }

    /**
     * 判断一个点是否能打中该船
     * @param x 行坐标
     * @param y 列坐标
     * @return 是否打中
     */
    public boolean isHit(int x,int y){
        if(x == start_x){
            return y >= start_y && y <= end_y;
        }
        else return y == start_y&&x>=start_x&&x<=end_x;
    }

    public int getStart_x() {
        return start_x;
    }

    public int getStart_y() {
        return start_y;
    }

    public int getEnd_x() {
        return end_x;
    }

    public int getEnd_y() {
        return end_y;
    }

    public int getLength() {
        return length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ship ship = (ship) o;
        return start_x == ship.start_x && start_y == ship.start_y && end_x == ship.end_x && end_y == ship.end_y && length == ship.length;
    }

    @Override
    public int hashCode() {
        return Objects.hash(start_x, start_y, end_x, end_y, length);
    }
}
