import styled from "@emotion/styled";
import { ChangeEvent, useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router";
import { joinGroup } from "../apis";
import Button from "../components/common/Button";
import Input from "../components/common/Input";
import MobileTemplate from "../components/common/MobileTemplate";
import ModalBox from "../components/common/ModalBox";
import { colors } from "../design-token";
import { ReactComponent as RightArrowIcon } from "../static/svg/rightArrowIcon.svg";

export function getCookie(cookieName: string) {
  var cookieValue = null;
  if (document.cookie) {
    var array = document.cookie.split(escape(cookieName) + "=");
    if (array.length >= 2) {
      var arraySub = array[1].split(";");
      cookieValue = unescape(arraySub[0]);
    }
  }
  return cookieValue;
}

const Join = () => {
  const { groupId } = useParams();
  const navigate = useNavigate();
  const [value, setValue] = useState({
    number: window.localStorage.myId,
    password: "",
  });

  const { number, password } = value;

  const onChangeHandler = (evt: ChangeEvent<HTMLInputElement>) => {
    setValue({ ...value, [evt.target.name]: evt.target.value });
  };

  useEffect(() => {
    document.body.style.overflow = "hidden";
    return () => {
      document.body.style.overflow = "unset";
    };
  }, []);

  const isFullFiled = number && password && true;

  const onSubmit = async () => {
    try {
      number &&
        groupId &&
        (await joinGroup(parseInt(number), parseInt(groupId)));
      number && groupId && navigate(`/group/${groupId}`);
    } catch (e: any) {
      alert(e.response.data.message);
    }
  };
  return (
    <MobileTemplate>
      <JoinStyle>
        <ModalBox title="기존 프로필 사용">
          <div className="input-box">
            <Input
              name="number"
              title="PROFILE NUMBER"
              onChange={onChangeHandler}
              value={number}
            />
            <Input
              name="password"
              title="PROFILE PASSWORD"
              onChange={onChangeHandler}
            />
          </div>
          <Button onClick={onSubmit} disabled={!(isFullFiled || false)}>
            프로필 사용하기
          </Button>
        </ModalBox>
        <div className="button-box">
          <div className="description">
            프로필이 없거나 새로운 프로필을 만들고 싶으신가요?
          </div>
          <div
            className="button-create-profile"
            onClick={() => navigate("/create")}
          >
            <div className="button-title">프로필 만들기</div>
            <RightArrowIcon />
          </div>
        </div>
      </JoinStyle>
    </MobileTemplate>
  );
};

export default Join;

const JoinStyle = styled.div`
  display: flex;
  flex-direction: column;
  align-content: center;
  justify-content: center;
  height: 100%;
  padding: 0 20px;
  flex: 1;
  & .input-box {
    margin-top: 24px;
    margin-bottom: 40px;
    display: flex;
    flex-direction: column;
    gap: 28px;
  }
  & .button-box {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-top: 40px;
    & .button-create-profile {
      display: flex;
      align-items: center;
      justify-content: space-between;
      height: 77px;
      cursor: pointer;
      background-color: white;
      width: 100%;
      padding: 0 24px;
      box-sizing: border-box;
      border-radius: 20px;
      margin-top: 16px;
    }
    & .button-title {
      font-weight: 700;
      color: ${colors.title};
      font-size: 20px;
    }
    & .description {
      color: ${colors.description};
    }
  }
`;
