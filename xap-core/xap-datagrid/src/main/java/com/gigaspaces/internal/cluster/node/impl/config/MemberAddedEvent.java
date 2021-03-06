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

package com.gigaspaces.internal.cluster.node.impl.config;

import com.gigaspaces.internal.cluster.node.impl.backlog.BacklogMemberLimitationConfig;
import com.gigaspaces.internal.cluster.node.impl.groups.IReplicationChannelDataFilter;


@com.gigaspaces.api.InternalApi
public class MemberAddedEvent {
    final private String _memberName;
    final private Object _customData;
    final private BacklogMemberLimitationConfig _backlogMemberLimitation;
    final private IReplicationChannelDataFilter _filter;
    final private DynamicSourceGroupMemberLifeCycle _lifeCycle;

    public MemberAddedEvent(String memberName, Object customData,
                            BacklogMemberLimitationConfig backlogMemberLimitation,
                            IReplicationChannelDataFilter filter,
                            DynamicSourceGroupMemberLifeCycle lifeCycle) {
        _memberName = memberName;
        _customData = customData;
        _backlogMemberLimitation = backlogMemberLimitation;
        _filter = filter;
        _lifeCycle = lifeCycle;
    }

    public String getMemberName() {
        return _memberName;
    }

    public Object getCustomData() {
        return _customData;
    }

    public BacklogMemberLimitationConfig getBacklogMemberLimitation() {
        return _backlogMemberLimitation;
    }

    public IReplicationChannelDataFilter getFilter() {
        return _filter;
    }

    public DynamicSourceGroupMemberLifeCycle getLifeCycle() {
        return _lifeCycle;
    }

}