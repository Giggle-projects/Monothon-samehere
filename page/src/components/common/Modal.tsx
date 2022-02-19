import styled from "@emotion/styled";
import { ChangeEvent, FC, useEffect, useState } from "react";
import { useNavigate } from "react-router";
import { createGroup } from "../../apis";
import { colors } from "../../design-token";
import Button from "./Button";
import Input from "./Input";
import ModalBox from "./ModalBox";

interface Props {
  closeModal: () => void;
  onSubmit: () => void;
}
const Modal: FC<Props> = ({ closeModal, onSubmit }) => {
  const navigate = useNavigate();
  const [value, setValue] = useState({
    name: "",
    password: "",
    passwordConfirm: "",
  });

  const { name, password, passwordConfirm } = value;

  const onChangeHandler = (evt: ChangeEvent<HTMLInputElement>) => {
    setValue({ ...value, [evt.target.name]: evt.target.value });
  };

  const _onSubmit = async () => {
    try {
      const { data } = await createGroup({ name, password });
      closeModal();
      navigate(`/group/${data.id}`);
    } catch (e) {
      alert(e);
    }
  };

  useEffect(() => {
    document.body.style.overflow = "hidden";
    return () => {
      document.body.style.overflow = "unset";
    };
  }, []);

  const isFullFiled = name && password && password === passwordConfirm && true;
  return (
    <ModalStyle>
      <ModalBox title="새로운 그룹" closeModal={closeModal}>
        <div className="input-box">
          <Input onChange={onChangeHandler} name="name" title="GROUP NAME" />
          <Input
            onChange={onChangeHandler}
            name="password"
            title="GROUP PASSWORD"
          />
          <Input
            onChange={onChangeHandler}
            name="passwordConfirm"
            title="GROUP PASSWORD CONFIRM"
          />
        </div>
        <Button disabled={!(isFullFiled || false)} onClick={_onSubmit}>
          그룹 만들기
        </Button>
      </ModalBox>
    </ModalStyle>
  );
};

export default Modal;

const ModalStyle = styled.div`
  position: absolute;
  display: flex;
  padding: 0 20px;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  background-color: ${colors.mainBG};
  flex-direction: column;
  width: 100%;
  height: 100%;
  box-sizing: border-box;
  z-index: 999;
  & .input-box {
    margin-top: 24px;
    margin-bottom: 40px;
    display: flex;
    flex-direction: column;
    gap: 28px;
  }
`;
