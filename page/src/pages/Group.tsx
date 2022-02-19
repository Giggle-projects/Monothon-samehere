import styled from "@emotion/styled";
import { useEffect, useState } from "react";
import { useParams } from "react-router";
import {
  getGroup,
  getGroupMembers,
  GetGroupMembersResponse,
  GetGroupResponse,
} from "../apis";
import MobileTemplate from "../components/common/MobileTemplate";
import GroupEmpty from "../components/GroupEmpty";
import SelectProfile from "../components/SelectProfile";
import UserProfile from "../components/UserProfile";

const Group = () => {
  const { groupId } = useParams();
  const [groupMembers, setGroupMembers] = useState<GetGroupMembersResponse[]>(
    []
  );
  const [selectId, setSelectId] = useState<number>();

  const [group, setGroup] = useState<GetGroupResponse>();
  useEffect(() => {
    groupId &&
      getGroup(parseInt(groupId)).then((res) => {
        setGroup(res.data);
      });
    groupId &&
      getGroupMembers(parseInt(groupId)).then((res) => {
        setGroupMembers(res.data);
        res.data.length > 0 && setSelectId(res.data[0].id);
      });
  }, [groupId]);
  return (
    <MobileTemplate>
      <SelectProfile
        title={group?.name}
        groupId={group?.id}
        users={groupMembers}
        selectUserId={selectId}
        onChange={(selectId) => setSelectId(selectId)}
      ></SelectProfile>

      {selectId && <UserProfile userId={selectId}></UserProfile>}
      {groupId && groupMembers.length === 0 && (
        <GroupEmpty groupId={parseInt(groupId)}></GroupEmpty>
      )}
    </MobileTemplate>
  );
};

export default Group;
