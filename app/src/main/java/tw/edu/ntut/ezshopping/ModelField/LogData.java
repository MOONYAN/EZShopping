package tw.edu.ntut.ezshopping.ModelField;

import java.util.List;

/**
 * Created by Owen on 08/08/2016.
 */
public class LogData
{
    private List<Record> _recordList;
    private int _totalCost;

    public List<Record> getRecordList()
    {
        return _recordList;
    }

    public void setRecordList(List<Record> recordList)
    {
        _recordList = recordList;
    }

    public int getTotalCost()
    {
        return _totalCost;
    }

    public void setTotalCost(int totalCost)
    {
        _totalCost = totalCost;
    }
}
