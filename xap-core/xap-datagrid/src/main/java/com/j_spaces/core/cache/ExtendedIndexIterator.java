/*
 * Copyright (c) 2008-2016, GigaSpaces Technologies, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


package com.j_spaces.core.cache;

import com.j_spaces.kernel.IStoredList;
import com.j_spaces.kernel.IStoredListIterator;

import java.util.Iterator;
import java.util.NavigableMap;

/**
 * @author Yechiel Fefer
 * @version 1.0
 * @since 5.0
 */
@com.gigaspaces.api.InternalApi
public class ExtendedIndexIterator<V>
        implements IExtendedIndexIterator<V> {
    private final Iterator<IStoredList<V>> _iter;
    private IStoredListIterator<V> _pos;
    private IStoredList<V> _entries;
    private boolean _eof;
    private V _res;
    private final TypeDataIndex _idx;
    private boolean _randomScan;


    public ExtendedIndexIterator(NavigableMap mapToScan, TypeDataIndex idx) {
        _iter = mapToScan.values().iterator();
        _idx = idx;
    }

    public boolean hasNext() {
        _res = nextValue();
        return _res != null;
    }

    public V next() {
        V res = _res;
        _res = null;
        return res;
    }

    private V nextValue() {
        V res = null;
        boolean newSl;
        while (true) {
            if (_eof)
                return null;
            newSl = false;
            if (_entries == null) {
                if (_iter.hasNext()) {
                    IStoredList entries = _iter.next();
                    if (entries.optimizeScanForSingleObject()) {
                        res = (V) entries.getObjectFromHead();
                        if (res != null)
                            return res;
                        newSl = true;
                    } else {
                        newSl = true;
                        _pos = entries.establishListScan(_randomScan /*random*/);
                        _entries = entries;
                    }
                } else {
                    _eof = true;
                    return null;
                }
            }
            //entries is a real SL
            if (!newSl)
                _pos = _entries.next(_pos);
            if (_pos == null) {
                _entries = null;
                continue;
            }
            res = _pos.getSubject();
            if (res != null)
                return res;

        }

    }


    /**
     * Release of this SLHolder resource
     *
     * @see com.j_spaces.kernel.pool.Resource#release()
     */
    public void release() {
        if (_entries != null)
            _entries.freeSLHolder(_pos);
        _pos = null;
        _entries = null;
    }

    /**
     * called by GC to free all acquired resources; For protection only - we shouldn't rely on the
     * JVM.
     *
     * @see #releaseScan() which should be called when scan has ended prematurely.
     */
    protected void finalize() {
        release();
    }

    /**
     * release SLHolder for this scan
     */
    public void releaseScan() {
        release();
    }


    public int getAlreadyMatchedFixedPropertyIndexPos() {
        if (_idx == null)
            return -1;
        return _idx.getPos();
    }

    @Override
    public String getAlreadyMatchedIndexPath() {
        return null;
    }

    public boolean isMultiValueIterator() {
        return true;
    }

    public void remove() {
        throw new RuntimeException(" not supported");
    }

    public boolean isAlreadyMatched() {
        return false;
    }

    public boolean isIterator() {
        return true;
    }

}
