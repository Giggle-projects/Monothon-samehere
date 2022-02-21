import styled from "@emotion/styled";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router";
import {createCard, editCard, getItems} from "../apis";
import Button from "../components/common/Button";
import DropDown from "../components/common/DropDown";
import Input from "../components/common/Input";
import MobileTemplate from "../components/common/MobileTemplate";
import RadioGroup from "../components/common/RadioGroup";
import {colors} from "../design-token";
import {ReactComponent as UploadIcon} from "../static/svg/uploadIcon.svg";

const Create = () => {
      const navigate = useNavigate();
      const [page, setPage] = useState(0);
      const [file, setFile] = useState();
      const [loading, setLoading] = useState(false);
      const [items, setItems] = useState([])
      const [values, setValues] = useState({})
      const [value, setValue] = useState({
        name: "",
        gender: "",
        job: "",
        email: "",
        password: "",
        passwordConfirm: ""
      });

      const {
        name,
        gender,
        job,
        email,
        password,
        passwordConfirm
      } = value;

      const isFullField =
          name &&
          gender &&
          job &&
          email &&
          password &&
          password === passwordConfirm &&
          true;

      const onChangeHandler = (evt) => {
        setValue({...value, [evt.target.name]: evt.target.value});
      };

      const onSubmit = async () => {
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
                cardItems: items.map(item => {
                  return {
                    itemId: item.id,
                    value: values[item.id]
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

      useEffect(() => {
        const init = async () => {
          const response = await getItems()
          setItems(response.data)

          const initValues = {}
          response.data.forEach(item => {
            if (item.itemChoices.length > 0) {
              initValues[item.id] = item.itemChoices[0]
            } else {
              initValues[item.id] = ""
            }
          });
          setValues(initValues);
        }
        init();
      }, []);

      return (
          <MobileTemplate>
            <CreateStyle>
              <div className="title">
                {page === 0 ? (
                    <>
                      편리한 정보 공유를 위해
                      <br/> 꼭 입력해주세요!
                    </>
                ) : (
                    <>
                      공유하고 싶은 정보를
                      <br/> 자유롭게 입력해주세요!
                      <div className="description">
                        공유를 원하지 않는 정보는 비워두거나
                        <br/> 항목을 삭제하시면 돼요!
                      </div>
                    </>
                )}
              </div>
              <div className="content">
                {page === 0 ? (
                    <>
                      <div className="profile-upload">
                        <div className="left">
                          <div className="title">프로필 사진 업로드</div>
                          <div className="description">
                            사진이 없으면 기본 프로필로 등록되어요!
                          </div>
                        </div>
                        <label className="upload-button">
                          <input
                              type="file"
                              accept="image/*"
                              onChange={(e) =>
                                  e.target.files && setFile(e.target.files[0])
                              }
                          />
                          <UploadIcon/>
                        </label>
                      </div>
                      <Input
                          value={name}
                          title="이름"
                          placeholder="이름을 입력해주세요."
                          name="name"
                          onChange={onChangeHandler}
                      />
                      <DropDown
                          onChange={onChangeHandler}
                          title="성별"
                          name="gender"
                          placeholder="성별을 선택하세요."
                          items={["남", "여"]}
                      />

                      <Input
                          value={job}
                          title="직업"
                          placeholder="직업을 입력해주세요. ex) Frontend Engineer"
                          name="job"
                          onChange={onChangeHandler}
                      />
                      <Input
                          value={email}
                          title="이메일"
                          placeholder="이메일을 입력해주세요."
                          name="email"
                          onChange={onChangeHandler}
                      />
                      <Input
                          title="비밀번호"
                          placeholder="비밀번호를 입력해주세요."
                          value={password}
                          name="password"
                          onChange={onChangeHandler}
                      />
                      <Input
                          title="비밀번호 확인"
                          placeholder="비밀번호를 확인해주세요."
                          value={passwordConfirm}
                          name="passwordConfirm"
                          onChange={onChangeHandler}
                      />
                    </>
                ) : (
                    <>
                      {items.map(item => {
                        if (item.itemChoices.length > 5) {
                          return <DropDown
                              title={item.name}
                              placeholder="선택해주세요."
                              name={item.name}
                              items={item.itemChoices}
                              onChange={(e) => {
                                const newValue = e.target.value
                                console.log(newValue)
                                setValues({...values, [item.id]: newValue})
                              }}
                          />
                        }
                        if (item.itemChoices.length > 0) {
                          return <RadioGroup
                              title={item.name}
                              items={item.itemChoices}
                              name={item.name}
                              onChange={(e) => {
                                const newValue = e.target.value
                                console.log(newValue)
                                setValues({...values, [item.id]: newValue})
                              }}
                          />
                        }
                        return <Input
                            title={item.name}
                            value={values[item.id].value}
                            name={item.name}
                            placeholder="입력해주세요."
                            onChange={(e) => {
                              const newValue = e.target.value
                              console.log(newValue)
                              setValues({...values, [item.id]: newValue})
                            }}
                        />
                      })}
                    </>
                )}
              </div>
              <div className="bottomButton">
                <Button
                    onClick={() => (page === 1 ? onSubmit() : setPage(1))}
                    disabled={loading ? true : !(isFullField || false)}
                >
                  {page === 0 ? "다음 내용을 입력하기" : "프로필 작성 마치기"}
                </Button>
              </div>
            </CreateStyle>
          </MobileTemplate>
      );
    }
;

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
