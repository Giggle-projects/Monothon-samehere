import {useEffect, useState} from "react";
import {createCard, editCard, getItems} from "../../apis";
import MobileTemplate from "../../components/common/MobileTemplate";
import FirstPage from "./FirstPage";
import SecondPage from "./SecondPage";
import {useNavigate} from "react-router";
import styled from "@emotion/styled";
import {colors} from "../../design-token";

const Create = () => {
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);
  const [page, setPage] = useState(0);
  const [file, setFile] = useState();
  const [additionalQuestions, setAdditionalQuestions] = useState([])
  const [additionalAnswers, setAdditionalAnswers] = useState({})
  const [essentialAnswers, setEssentialAnswers] = useState({});

  const onChangeEssentialAnswer = (e) => {
    setEssentialAnswers(
        {...essentialAnswers, [e.target.name]: e.target.value});
  };

  const onChangeAdditionalAnswer = (item) => {
    return (e) => {
      setAdditionalAnswers(
          {...additionalAnswers, [item.id]: e.target.value})
    };
  }

  useEffect(() => {
    const init = async () => {
      const response = await getItems()
      setAdditionalQuestions(response.data)

      const initValues = {}
      response.data.forEach(question => {
        if (question.itemChoices.length > 0) {
          initValues[question.id] = question.itemChoices[0]
        } else {
          initValues[question.id] = ""
        }
      });
      setAdditionalAnswers(initValues);
    }
    init();
  }, []);

  const onSubmit = async () => {
    const {name, gender, job, email, password} = essentialAnswers;
    const formData = new FormData();
    formData.set("name", name);
    formData.set("gender", gender);
    formData.set("email", email);
    formData.set("profession", job);
    formData.set("password", password);
    file && formData.append("image", file);

    try {
      setLoading(true);
      const {data} = await createCard(formData);
      await editCard(data.id, {
            name: name,
            gender: gender,
            email: email,
            profession: job,
            password: password,
            cardItems: additionalQuestions.map(question => {
              return {
                itemId: question.id,
                value: additionalAnswers[question.id]
              }
            })
          }
      );
      navigate(`/profile/${data.id}`);
    } catch (e) {
      alert(e.response.data.message);
      setPage(0);
    }
    setLoading(false);
  };

  return (
      <MobileTemplate>
        <CreateStyle>
          {page === 0 ? (
              <FirstPage
                  essentialAnswers={essentialAnswers}
                  setFile={file => setFile(file)}
                  setPage={page => setPage(page)}
                  onChangeEssentialAnswer={onChangeEssentialAnswer}
                  file={file}
              />)
              : (<SecondPage
                  essentialAnswers={essentialAnswers + file}
                  additionalQuestions={additionalQuestions}
                  additionalAnswers={additionalAnswers}
                  onChangeAdditionalAnswer={onChangeAdditionalAnswer}
                  onSubmit={onSubmit}
                  loading={loading}
              />)
          }
        </CreateStyle>
      </MobileTemplate>
  )
};

export default Create;

const CreateStyle = styled.div`
  display: flex;
  flex-direction: column;
  height: 100%;
  & .bottomButton {
    position: fixed;
    bottom: 0;
    max-width: 710px;
    width: 100%;
    padding: 0 28px;
    padding-bottom: 28px;
    box-sizing: border-box;
    background-color: white;
  }
  & .profile-upload {
    display: flex;
    align-items: center;
    justify-content: space-between;
    & .left {
      & > .title {
        color: ${colors.title};
        font-weight: 700;
        font-size: 16px;
      }
      & > .description {
        color: ${colors.description};
        font-weight: 400;
        font-size: 12px;
      }
    }
    & .upload-button {
      & input {
        display: none;
      }
      width: 44px;
      height: 44px;
      background-color: ${colors.icon};
      border: none;
      cursor: pointer;
      border-radius: 8px;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    & img {
      min-width: 100px;
      min-height: 100px;
      width: 100px;
      height: 100px;
      object-fit: cover;
    }
  }
  & > .title {
    color: ${colors.title};
    font-weight: 700;
    font-size: 20px;
    padding: 32px 28px;
    & .description {
      color: ${colors.description};
      font-weight: 400;
      font-size: 12px;
      margin-top: 8px;
    }
  }
  & .content {
    padding: 0 28px;
    padding-top: 40px;
    padding-bottom: 112px;
    flex: 1;
    background-color: white;
    border-radius: 24px;
    display: flex;
    flex-direction: column;
    gap: 28px;
  }
`;

