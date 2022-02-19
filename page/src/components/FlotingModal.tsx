import styled from "@emotion/styled";
import { FC, useEffect } from "react";

const FlotingModal: FC = ({ children }) => {
  useEffect(() => {
    document.body.style.overflow = "hidden";
    return () => {
      document.body.style.overflow = "unset";
    };
  }, []);
  return <FlotingModalStyle>{children}</FlotingModalStyle>;
};

export default FlotingModal;

const FlotingModalStyle = styled.div`
  position: absolute;
  display: flex;
  background-color: white;
  flex-direction: column;
  width: 100%;
  bottom: 0;
  box-sizing: border-box;
  gap: 24px;
  border-radius: 12px;
  box-shadow: 0px 0px 40px rgba(0, 0, 0, 0.1),
    0px -10px 120px rgba(0, 0, 0, 0.3);
  padding: 32px 28px;
`;
